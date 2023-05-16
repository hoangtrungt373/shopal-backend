package vn.group24.shopalbackend.mapper.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.admin.CustomerAllInfoDto;
import vn.group24.shopalbackend.controller.response.admin.MembershipPointDto;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Membership;

/**
 *
 * @author ttg
 */
@Component
public class CustomerAllInfoMapper {

    public List<CustomerAllInfoDto> mapToCustomerAllInfoDtos(List<Customer> customers) {
        List<CustomerAllInfoDto> customerAllInfoDtos = new ArrayList<>();
        customers.forEach(customer -> {
            CustomerAllInfoDto customerAllInfoDto = new CustomerAllInfoDto();
            customerAllInfoDto.setId(customer.getId());
            customerAllInfoDto.setBirthDate(customer.getBirthDate());
            customerAllInfoDto.setAddress(customer.getAddress());
            customerAllInfoDto.setAvatarUrl(customer.getAvatarUrl());
            customerAllInfoDto.setGender(customer.getGender());
            customerAllInfoDto.setGenderDescription(customer.getGender().getTextForCurrentLan());
            customerAllInfoDto.setFullName(customer.getFullName());
            customerAllInfoDto.setNickName(customer.getNickName());
            customerAllInfoDto.setPhoneNumber(customer.getPhoneNumber());
            customerAllInfoDto.setContactEmail(customer.getContactEmail());
            customerAllInfoDto.setJoinDate(customer.getJoinDate());
            customerAllInfoDto.setTotalBuy(customer.getTotalBuy());
            customerAllInfoDto.setMembershipPoints(customer.getMemberships().stream().map(this::mapToMembershipPoint).toList());
            customerAllInfoDtos.add(customerAllInfoDto);
        });
        return customerAllInfoDtos;
    }

    public MembershipPointDto mapToMembershipPoint(Membership membership) {
        MembershipPointDto membershipPointDto = new MembershipPointDto();
        membershipPointDto.setMembershipId(membership.getId());
        membershipPointDto.setEnterpriseId(membership.getEnterprise().getId());
        membershipPointDto.setAvailablePoint(membership.getAvailablePoint());
        membershipPointDto.setEnterpriseName(membership.getEnterprise().getEnterpriseName());
        membershipPointDto.setEnterpriseLogoUrl(membership.getEnterprise().getLogoUrl());
        membershipPointDto.setTotalBuy(membership.getTotalBuy());
        return membershipPointDto;
    }
}
