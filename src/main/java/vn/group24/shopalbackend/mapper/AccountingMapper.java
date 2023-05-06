package vn.group24.shopalbackend.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.enterprise.EnterpriseAccountingDto;
import vn.group24.shopalbackend.domain.Accounting;
import vn.group24.shopalbackend.domain.multilingual.PaymentStatusLan;
import vn.group24.shopalbackend.util.LanguageUtils;

/**
 *
 * @author ttg
 */
@Component
public class AccountingMapper {

    @Autowired
    private LanguageUtils languageUtils;

    public List<EnterpriseAccountingDto> mapToEnterpriseAccountingDtos(List<Accounting> accountings) {
        return accountings.stream().map(accounting -> {
            EnterpriseAccountingDto enterpriseAccountingDto = new EnterpriseAccountingDto();
            enterpriseAccountingDto.setId(accounting.getId());
            enterpriseAccountingDto.setStartDate(accounting.getStartDate());
            enterpriseAccountingDto.setEndDate(accounting.getEndDate());
            enterpriseAccountingDto.setPaymentStatus(accounting.getPaymentStatus());
            enterpriseAccountingDto.setPaymentStatusDescription(languageUtils.getEnumDescription(accounting.getPaymentStatus(), PaymentStatusLan.TABLE_NAME));
            enterpriseAccountingDto.setPaymentDate(accounting.getPaymentDate());
            enterpriseAccountingDto.setPaymentMethod(accounting.getPaymentMethod());
            enterpriseAccountingDto.setTotalIncome(accounting.getTotalIncome());
            enterpriseAccountingDto.setTotalCommission(accounting.getTotalCommission());
            enterpriseAccountingDto.setCommissionRate(accounting.getCommissionRate());
            return enterpriseAccountingDto;
        }).collect(Collectors.toList());
    }
}
