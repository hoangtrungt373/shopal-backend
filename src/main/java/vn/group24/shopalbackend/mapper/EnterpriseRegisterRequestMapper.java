package vn.group24.shopalbackend.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.admin.EnterpriseRegisterRequestAnnDto;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.dto.EnterpriseRegisterRequestAnn;

/**
 *
 * @author ttg
 */
@Component
public class EnterpriseRegisterRequestMapper {

    public List<EnterpriseRegisterRequestAnnDto> mapToEnterpriseRegisterRequestAnnDto(List<EnterpriseRegisterRequestAnn> enterpriseRegisterRequestAnns) {
        return enterpriseRegisterRequestAnns.stream().map(enterpriseCooperationRequest -> {
            EnterpriseRegisterRequestAnnDto enterpriseRegisterRequestAnnDto = new EnterpriseRegisterRequestAnnDto();
            // TODO set id and enterprise
            enterpriseRegisterRequestAnnDto.setFullName(enterpriseCooperationRequest.getFullName());
            enterpriseRegisterRequestAnnDto.setPosition(enterpriseCooperationRequest.getPosition());
            enterpriseRegisterRequestAnnDto.setWorkEmail(enterpriseCooperationRequest.getWorkEmail());
            enterpriseRegisterRequestAnnDto.setPhoneNumber(enterpriseCooperationRequest.getPhoneNumber());
            enterpriseRegisterRequestAnnDto.setEnterpriseAddress(enterpriseCooperationRequest.getEnterpriseAddress());
            enterpriseRegisterRequestAnnDto.setEnterpriseName(enterpriseCooperationRequest.getEnterpriseName());
            enterpriseRegisterRequestAnnDto.setEnterpriseWebsite(enterpriseCooperationRequest.getEnterpriseWebsite());
            enterpriseRegisterRequestAnnDto.setRegisterRequestStatus(enterpriseCooperationRequest.getRegisterRequestStatus());
            enterpriseRegisterRequestAnnDto.setRegisterRequestStatusDescription(enterpriseCooperationRequest.getRegisterRequestStatus().getTextForCurrentLan());
            enterpriseRegisterRequestAnnDto.setRegisterDate(enterpriseCooperationRequest.getRegisterDate());
            enterpriseRegisterRequestAnnDto.setVerificationDate(enterpriseCooperationRequest.getVerificationDate());
            enterpriseRegisterRequestAnnDto.setRegisterDate(enterpriseCooperationRequest.getRegisterDate());
            return enterpriseRegisterRequestAnnDto;
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
