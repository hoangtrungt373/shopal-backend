package vn.group24.shopalbackend.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.common.AccountingDto;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.domain.Accounting;
import vn.group24.shopalbackend.domain.Enterprise;

/**
 *
 * @author ttg
 */
@Component
public class AccountingMapper extends AbstractMapper {

    public List<AccountingDto> mapToAccountingDtos(List<Accounting> accountings) {
        return accountings.stream().map(accounting -> {
            AccountingDto accountingDto = new AccountingDto();
            accountingDto.setId(accounting.getId());
            accountingDto.setStartDate(accounting.getStartDate());
            accountingDto.setEndDate(accounting.getEndDate());
            accountingDto.setPaymentStatus(accounting.getPaymentStatus());
            accountingDto.setPaymentStatusDescription(getTextForCurrentLan(accounting.getPaymentStatus()));
            accountingDto.setPaymentDate(accounting.getPaymentDate());
            accountingDto.setPaymentMethod(accounting.getPaymentMethod());
            accountingDto.setTotalIncome(accounting.getTotalIncome());
            accountingDto.setTotalCommission(accounting.getTotalCommission());
            accountingDto.setCommissionRate(accounting.getCommissionRate());
            accountingDto.setEnterprise(mapToEnterpriseDto(accounting.getEnterprise()));
            return accountingDto;
        }).toList();
    }

    private EnterpriseDto mapToEnterpriseDto(Enterprise enterprise) {
        EnterpriseDto enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(enterprise.getId());
        enterpriseDto.setEnterpriseName(enterprise.getEnterpriseName());
        enterpriseDto.setLogoUrl(enterprise.getLogoUrl());
        return enterpriseDto;
    }
}
