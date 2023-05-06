package vn.group24.shopalbackend.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.enterprise.EnterpriseCooperationContractDto;
import vn.group24.shopalbackend.domain.CooperationContract;
import vn.group24.shopalbackend.domain.multilingual.ContractStatusLan;
import vn.group24.shopalbackend.util.LanguageUtils;

/**
 *
 * @author ttg
 */
@Component
public class CooperationContractMapper {

    @Autowired
    private LanguageUtils languageUtils;

    public List<EnterpriseCooperationContractDto> mapToEnterpriseCooperationContractDto(List<CooperationContract> cooperationContracts) {
        return cooperationContracts.stream().map(cooperationContract -> {
            EnterpriseCooperationContractDto enterpriseCooperationContractDto = new EnterpriseCooperationContractDto();
            enterpriseCooperationContractDto.setContractStatus(cooperationContract.getContractStatus());
            enterpriseCooperationContractDto.setId(cooperationContract.getId());
            enterpriseCooperationContractDto.setCommissionRate(cooperationContract.getCommissionRate());
            enterpriseCooperationContractDto.setStartDate(cooperationContract.getStartDate());
            enterpriseCooperationContractDto.setEndDate(cooperationContract.getEndDate());
            enterpriseCooperationContractDto.setContractStatusDescription(languageUtils.getEnumDescription(cooperationContract.getContractStatus(), ContractStatusLan.TABLE_NAME));
            enterpriseCooperationContractDto.setCashPerPoint(cooperationContract.getCashPerPoint());
            enterpriseCooperationContractDto.setUpdateDescription(cooperationContract.getUpdateDescription());
            return enterpriseCooperationContractDto;
        }).toList();
    }
}
