package vn.group24.shopalbackend.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.CooperationContractDto;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.domain.CooperationContract;
import vn.group24.shopalbackend.domain.Enterprise;

/**
 *
 * @author ttg
 */
@Component
public class CooperationContractMapper {

    public List<CooperationContractDto> mapToCooperationContractDtos(List<CooperationContract> cooperationContracts) {
        return cooperationContracts.stream().map(cooperationContract -> {
            CooperationContractDto cooperationContractDto = new CooperationContractDto();
            cooperationContractDto.setEnterprise(mapToEnterpriseDto(cooperationContract.getEnterprise()));
            cooperationContractDto.setContractStatus(cooperationContract.getContractStatus());
            cooperationContractDto.setId(cooperationContract.getId());
            cooperationContractDto.setCommissionRate(cooperationContract.getCommissionRate());
            cooperationContractDto.setStartDate(cooperationContract.getStartDate());
            cooperationContractDto.setEndDate(cooperationContract.getEndDate());
            cooperationContractDto.setContractStatusDescription(cooperationContract.getContractStatus().getTextForCurrentLan());
            cooperationContractDto.setCashPerPoint(cooperationContract.getCashPerPoint());
            return cooperationContractDto;
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
