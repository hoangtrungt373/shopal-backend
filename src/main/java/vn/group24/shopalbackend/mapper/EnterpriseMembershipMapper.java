package vn.group24.shopalbackend.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.controller.response.customer.EnterpriseMembershipDto;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.Membership;

/**
 *
 * @author ttg
 */
@Component
public class EnterpriseMembershipMapper {

    public List<EnterpriseMembershipDto> mapToEnterpriseMembershipDto(List<Membership> memberships) {
        List<EnterpriseMembershipDto> enterpriseMembershipDtos = new ArrayList<>();
        memberships.forEach(membership -> {
            EnterpriseMembershipDto enterpriseMembershipDto = new EnterpriseMembershipDto();
            enterpriseMembershipDto.setId(membership.getId());
            enterpriseMembershipDto.setRegisterEmail(membership.getRegisterEmail());
            enterpriseMembershipDto.setRegisterPhoneNumber(membership.getRegisterPhoneNumber());
            enterpriseMembershipDto.setAvailablePoint(membership.getAvailablePoint());
            enterpriseMembershipDto.setEnterprise(mapToEnterpriseDto(membership.getEnterprise()));
            enterpriseMembershipDtos.add(enterpriseMembershipDto);
        });

        return enterpriseMembershipDtos;
    }

    public EnterpriseDto mapToEnterpriseDto(Enterprise enterprise) {
        EnterpriseDto enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(enterprise.getId());
        enterpriseDto.setLogoUrl(enterprise.getLogoUrl());
        enterpriseDto.setEnterpriseName(enterprise.getEnterpriseName());
        return enterpriseDto;
    }
}
