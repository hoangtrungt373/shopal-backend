package vn.group24.shopalbackend.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.controller.response.enterprise.EnterpriseCooperationContractDto;
import vn.group24.shopalbackend.domain.CooperationContract;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.enums.ContractStatus;

/**
 *
 * @author ttg
 */
@Component
public class EnterpriseMapper {

    public List<EnterpriseDto> mapToEnterpriseDtos(List<Enterprise> enterprises) {
        return enterprises.stream().map(enterprise -> {
            EnterpriseDto enterpriseDto = new EnterpriseDto();
            enterpriseDto.setId(enterprise.getId());
            enterpriseDto.setEnterpriseName(enterprise.getEnterpriseName());
            enterpriseDto.setLogoUrl(enterprise.getLogoUrl());
            return enterpriseDto;
        }).collect(Collectors.toList());
    }

    public EnterpriseDto mapToEnterpriseDto(Enterprise enterprise) {
        EnterpriseDto enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(enterprise.getId());
        enterpriseDto.setEnterpriseName(enterprise.getEnterpriseName());
        enterpriseDto.setLogoUrl(enterprise.getLogoUrl());
        enterprise.getCooperationContracts().stream()
                .filter(cc -> ContractStatus.ACTIVE == cc.getContractStatus())
                .findFirst()
                .map(this::mapToEnterpriseCooperationContractDto)
                .ifPresent(enterpriseDto::setContract);
        return enterpriseDto;
    }

    public EnterpriseCooperationContractDto mapToEnterpriseCooperationContractDto(CooperationContract cooperationContract) {
        EnterpriseCooperationContractDto enterpriseCooperationContractDto = new EnterpriseCooperationContractDto();
        enterpriseCooperationContractDto.setContractStatus(cooperationContract.getContractStatus());
        enterpriseCooperationContractDto.setId(cooperationContract.getId());
        enterpriseCooperationContractDto.setCommissionRate(cooperationContract.getCommissionRate());
        enterpriseCooperationContractDto.setStartDate(cooperationContract.getStartDate());
        enterpriseCooperationContractDto.setEndDate(cooperationContract.getEndDate());
        enterpriseCooperationContractDto.setContractStatusDescription(cooperationContract.getContractStatus().getTextForCurrentLan());
        enterpriseCooperationContractDto.setCashPerPoint(cooperationContract.getCashPerPoint());
        enterpriseCooperationContractDto.setUpdateDescription(cooperationContract.getUpdateDescription());
        return enterpriseCooperationContractDto;
    }
}
