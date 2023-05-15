package vn.group24.shopalbackend.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.admin.EnterpriseCooperationRequestDto;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.notification.EnterpriseCooperationRequest;

/**
 *
 * @author ttg
 */
@Component
public class EnterpriseCooperationRequestMapper {

    public List<EnterpriseCooperationRequestDto> mapToEnterpriseCooperationContractDto(List<EnterpriseCooperationRequest> enterpriseCooperationRequests) {
        return enterpriseCooperationRequests.stream().map(enterpriseCooperationRequest -> {
            EnterpriseCooperationRequestDto enterpriseCooperationRequestDto = new EnterpriseCooperationRequestDto();
            enterpriseCooperationRequestDto.setId(enterpriseCooperationRequest.getId());
            enterpriseCooperationRequestDto.setFullName(enterpriseCooperationRequest.getFullName());
            enterpriseCooperationRequestDto.setPosition(enterpriseCooperationRequest.getPosition());
            enterpriseCooperationRequestDto.setWorkEmail(enterpriseCooperationRequest.getWorkEmail());
            enterpriseCooperationRequestDto.setPhoneNumber(enterpriseCooperationRequest.getPhoneNumber());
            enterpriseCooperationRequestDto.setEnterpriseAddress(enterpriseCooperationRequest.getEnterpriseAddress());
            enterpriseCooperationRequestDto.setEnterpriseName(enterpriseCooperationRequest.getEnterpriseName());
            enterpriseCooperationRequestDto.setEnterpriseWebsite(enterpriseCooperationRequest.getEnterpriseWebsite());
            enterpriseCooperationRequestDto.setRegisterRequestStatus(enterpriseCooperationRequest.getRegisterRequestStatus());
            enterpriseCooperationRequestDto.setRegisterRequestStatusDescription(enterpriseCooperationRequest.getRegisterRequestStatus().getTextForCurrentLan());
            enterpriseCooperationRequestDto.setRegisterDate(enterpriseCooperationRequest.getRegisterDate());
            enterpriseCooperationRequestDto.setVerificationDate(enterpriseCooperationRequest.getVerificationDate());
            enterpriseCooperationRequestDto.setRegisterDate(enterpriseCooperationRequest.getRegisterDate());
            return enterpriseCooperationRequestDto;
        }).toList();
    }

    public EnterpriseDto mapToEnterpriseDto(Enterprise enterprise) {
        EnterpriseDto enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(enterprise.getId());
        enterpriseDto.setLogoUrl(enterprise.getLogoUrl());
        enterpriseDto.setEnterpriseName(enterprise.getEnterpriseName());
        return enterpriseDto;
    }
}
