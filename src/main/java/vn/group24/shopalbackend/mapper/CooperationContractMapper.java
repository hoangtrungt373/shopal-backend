package vn.group24.shopalbackend.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.enterprise.EnterpriseCooperationContractDto;
import vn.group24.shopalbackend.domain.CooperationContract;

/**
 *
 * @author ttg
 */
@Component
public class CooperationContractMapper {

    public List<EnterpriseCooperationContractDto> mapToEnterpriseCooperationContractDto(List<CooperationContract> cooperationContracts) {
        return cooperationContracts.stream().map(cooperationContract -> {
            EnterpriseCooperationContractDto enterpriseCooperationContractDto = new EnterpriseCooperationContractDto();
            enterpriseCooperationContractDto.setContractStatus(cooperationContract.getContractStatus());
            enterpriseCooperationContractDto.setId(cooperationContract.getId());
            enterpriseCooperationContractDto.setCommissionRate(cooperationContract.getCommissionRate());
            enterpriseCooperationContractDto.setStartDate(cooperationContract.getStartDate());
            enterpriseCooperationContractDto.setEndDate(cooperationContract.getEndDate());
            enterpriseCooperationContractDto.setContractStatusDescription(cooperationContract.getContractStatus().getTextForCurrentLan());
            enterpriseCooperationContractDto.setCashPerPoint(cooperationContract.getCashPerPoint());
            return enterpriseCooperationContractDto;
        }).toList();
    }
}
