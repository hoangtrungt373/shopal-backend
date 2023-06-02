package vn.group24.shopalbackend.service.impl;

import static vn.group24.shopalbackend.util.VnPayConfig.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import vn.group24.shopalbackend.controller.request.AccountingCalculateRequest;
import vn.group24.shopalbackend.controller.request.AccountingSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.request.PurchaseOrderSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.common.AccountingDto;
import vn.group24.shopalbackend.domain.Accounting;
import vn.group24.shopalbackend.domain.CooperationContract;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.PurchaseOrder;
import vn.group24.shopalbackend.domain.dto.VnPayPaymentResult;
import vn.group24.shopalbackend.domain.enums.ContractStatus;
import vn.group24.shopalbackend.domain.enums.OrderStatus;
import vn.group24.shopalbackend.domain.enums.PaymentMethod;
import vn.group24.shopalbackend.domain.enums.PaymentStatus;
import vn.group24.shopalbackend.mapper.AccountingMapper;
import vn.group24.shopalbackend.repository.AccountingRepository;
import vn.group24.shopalbackend.repository.CooperationContractRepository;
import vn.group24.shopalbackend.repository.PurchaseOrderRepository;
import vn.group24.shopalbackend.service.AccountingService;
import vn.group24.shopalbackend.service.VnPayService;

/**
 *
 * @author ttg
 */
@Service
@Transactional
public class AccountingServiceImpl implements AccountingService {

    @Autowired
    private AccountingRepository accountingRepository;
    @Autowired
    private CooperationContractRepository cooperationContractRepository;

    @Autowired
    private VnPayService vnPayService;
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private AccountingMapper accountingMapper;
    @Autowired
    private ObjectMapper jsonObjectMapper;

    @Override
    public List<AccountingDto> getAccountingByCriteria(AccountingSearchCriteriaRequest criteria) {
        List<Accounting> accountings = accountingRepository.getByCriteria(criteria).stream()
                .sorted(Comparator.comparing(Accounting::getStartDate).reversed().thenComparing(Accounting::getPaymentStatus)).collect(Collectors.toList());
        accountings.forEach(accounting -> {
            BigDecimal commissionRate = cooperationContractRepository.getByStartDateAfterAndEndDateBefore(accounting.getStartDate().minusDays(1), accounting.getEndDate().plusDays(1))
                    .stream().findFirst().map(CooperationContract::getCommissionRate)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Cooperation Contract not found for period [%s, %s]", accounting.getStartDate(), accounting.getEndDate())));
            accounting.setCommissionRate(commissionRate);
        });
        return accountingMapper.mapToAccountingDtos(accountings);
    }

    @Override
    public String calculateAccounting(AccountingCalculateRequest request) { // must be whole month
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        Map<Enterprise, List<PurchaseOrder>> purchaseOrdersMap = purchaseOrderRepository.getByCriteria(PurchaseOrderSearchCriteriaRequest.builder()
                .orderDateFrom(LocalDateTime.from(startDate))
                .orderDateTo(LocalDateTime.from(endDate))
                .orderStatus(OrderStatus.DELIVERED)
                .build()).stream().collect(Collectors.groupingBy(PurchaseOrder::getEnterprise));

        List<Accounting> accountings = new ArrayList<>();
        purchaseOrdersMap.forEach(((enterprise, purchaseOrders) -> {

            CooperationContract activeContract = cooperationContractRepository.getByEnterpriseIdAndContractStatus(enterprise.getId(), ContractStatus.ACTIVE);

            if (activeContract != null) {
                Accounting accounting = new Accounting();
                accounting.setEnterprise(enterprise);
                accounting.setStartDate(startDate);
                accounting.setEndDate(endDate);
                accounting.setPaymentStatus(PaymentStatus.UNPAID);
                accounting.setCommissionRate(activeContract.getCommissionRate());
                accounting.setTotalIncome(purchaseOrders.stream().map(PurchaseOrder::getOrderTotalCash).reduce(BigDecimal.ZERO, BigDecimal::add));
                accounting.setTotalCommission(accounting.getCommissionRate().multiply(accounting.getTotalCommission()));
                accountings.add(accounting);
            }
        }));

        accountingRepository.saveAll(accountings);

        return "Calculate accounting done";
    }

    @Override
    public String getVnPayPaymentUrl(AccountingDto accountingDto, HttpServletRequest request) throws JsonProcessingException {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return vnPayService.createOrder(Integer.parseInt(accountingDto.getTotalCommission().toString()), accountingDto.getId().toString(), baseUrl);
    }

    @Override
    public VnPayPaymentResult saveVnPayPaymentResult(HttpServletRequest request) throws JsonProcessingException {
        int accountingId = Integer.parseInt(request.getParameter(VNP_ORDERINFO_STR));
        Accounting accounting = accountingRepository.findById(accountingId).orElseGet(() -> null);
        Validate.isTrue(accounting != null, "Can not found Accounting with id = %s", accountingId);
        VnPayPaymentResult result = mapToVnPayPaymentResul(request);
        accounting.setPaymentDate(LocalDateTime.now());
        accounting.setPaymentStatus(PaymentStatus.PAID);
        accounting.setPaymentMethod(PaymentMethod.VNPAY);
        accounting.setPaymentDetail(jsonObjectMapper.writeValueAsString(result));
        accountingRepository.save(accounting);
        return result;
    }

    private VnPayPaymentResult mapToVnPayPaymentResul(HttpServletRequest request) {
        VnPayPaymentResult result = new VnPayPaymentResult();
        result.setVnpVersion(VNP_VERSION);
        result.setVnpCommand(VNP_COMMAND);
        result.setVnpTmnCode(VNP_TMNCODE);
        result.setVnpAmount(request.getParameter(VNP_AMOUNT_STR));
        result.setVnpCurrCode(request.getParameter(VNP_CURRCODE_STR));
        result.setVnpLocale(request.getParameter(VNP_LOCALE_STR));
        result.setVnpIpAddr(request.getParameter(VNP_IDADDR));
        result.setVnpCreateDate(request.getParameter(VNP_CREATEDATE_STR));
        result.setVnpExpireDate(request.getParameter(VNP_EXPIREDATE_STR));
        result.setVnpSecureHash(request.getParameter(VNP_SECUREHASH_STR));
        result.setVnpSecureHashType(request.getParameter(VNP_SECUREHASHTYPE_STR));
        result.setVnpTransactionStatus(request.getParameter(VNP_TRANSACTIONSTATUS_STR));
        result.setVnpPayDate(request.getParameter(VNP_PAYDATE_STR));
        result.setVnpTransactionNo(request.getParameter(VNP_TRANSACTIONNO_STR));
        return result;
    }
}
