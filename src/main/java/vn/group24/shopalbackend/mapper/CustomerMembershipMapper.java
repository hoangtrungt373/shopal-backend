package vn.group24.shopalbackend.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.enterprise.CustomerMembershipDto;
import vn.group24.shopalbackend.controller.response.enterprise.CustomerRegisterDto;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.multilingual.GenderLan;
import vn.group24.shopalbackend.domain.staging.StagCustomer;
import vn.group24.shopalbackend.util.LanguageUtils;

/**
 *
 * @author ttg
 */
@Component
public class CustomerMembershipMapper {

    @Autowired
    private LanguageUtils languageUtils;

    public List<CustomerMembershipDto> mapToCustomerMembershipDtos(Enterprise enterprise) {
        List<CustomerMembershipDto> customerMembershipDtos = new ArrayList<>();
        enterprise.getMemberships().forEach(membership -> {
            CustomerMembershipDto customerMembershipDto = new CustomerMembershipDto();
            customerMembershipDto.setId(membership.getId());
            customerMembershipDto.setCustomerId(membership.getCustomer().getId());
            customerMembershipDto.setFullName(membership.getCustomer().getFullName());
            customerMembershipDto.setRegisterEmail(membership.getRegisterEmail());
            customerMembershipDto.setRegisterPhoneNumber(membership.getRegisterPhoneNumber());
            customerMembershipDto.setAddress(membership.getCustomer().getAddress());
            customerMembershipDto.setGender(membership.getCustomer().getGender());
            customerMembershipDto.setGenderDescription(languageUtils.getEnumDescription(membership.getCustomer().getGender(), GenderLan.TABLE_NAME));
            customerMembershipDto.setBirthDate(membership.getCustomer().getBirthDate());
            customerMembershipDto.setAvatarUrl(membership.getCustomer().getAvatarUrl());
            customerMembershipDto.setAvailablePoint(membership.getAvailablePoint());
            customerMembershipDtos.add(customerMembershipDto);
        });

        return customerMembershipDtos;
    }

    public List<CustomerRegisterDto> mapToCustomerRegisterDtos(List<StagCustomer> stagCustomers) {
        List<CustomerRegisterDto> customerRegisterDtos = new ArrayList<>();
        stagCustomers.forEach(stagCustomer -> {
            CustomerRegisterDto customerRegisterDto = new CustomerRegisterDto();
            customerRegisterDto.setId(stagCustomer.getId());
            customerRegisterDto.setStagCustomerId(stagCustomer.getId());
            customerRegisterDto.setFullName(stagCustomer.getFullName());
            customerRegisterDto.setRegisterEmail(stagCustomer.getRegisterEmail());
            customerRegisterDto.setRegisterPhoneNumber(stagCustomer.getRegisterPhoneNumber());
            customerRegisterDto.setImportDate(stagCustomer.getImportDate());
            customerRegisterDto.setInitialPoint(stagCustomer.getInitialPoint());
            customerRegisterDtos.add(customerRegisterDto);
        });

        return customerRegisterDtos;
    }
}
