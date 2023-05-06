package vn.group24.shopalbackend.service.impl;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.group24.shopalbackend.controller.response.enterprise.EnterpriseAccountingDto;
import vn.group24.shopalbackend.domain.Accounting;
import vn.group24.shopalbackend.domain.CooperationContract;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.mapper.AccountingMapper;
import vn.group24.shopalbackend.repository.AccountingRepository;
import vn.group24.shopalbackend.repository.CooperationContractRepository;
import vn.group24.shopalbackend.service.AccountingService;

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
    private AccountingMapper accountingMapper;

    @Override
    public List<EnterpriseAccountingDto> getAllAccountingForEnterprise(Enterprise enterprise) {
        List<Accounting> accountings = accountingRepository.getByCriteria(enterprise.getId()).stream()
                .sorted(Comparator.comparing(Accounting::getEndDate).reversed()).collect(Collectors.toList());
        accountings.forEach(accounting -> {
            BigDecimal commissionRate = cooperationContractRepository.getByStartDateAfterAndEndDateBefore(accounting.getStartDate().minusDays(1), accounting.getEndDate().plusDays(1))
                    .stream().findFirst().map(CooperationContract::getCommissionRate)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Cooperation Contract not found for period [%s, %s]", accounting.getStartDate(), accounting.getEndDate())));
            accounting.setCommissionRate(commissionRate);
        });
        return accountingMapper.mapToEnterpriseAccountingDtos(accountings);
    }
}
