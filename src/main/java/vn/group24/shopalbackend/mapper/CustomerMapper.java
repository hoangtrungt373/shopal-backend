package vn.group24.shopalbackend.mapper;

import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.common.CustomerDto;
import vn.group24.shopalbackend.domain.Customer;

/**
 *
 * @author ttg
 */
@Component
public class CustomerMapper {

    public CustomerDto mapToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setContactEmail(customer.getContactEmail());
        customerDto.setId(customer.getId());
        customerDto.setFullName(customer.getFullName());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setAddress(customer.getAddress());
        customerDto.setAvatarUrl(customer.getAvatarUrl());
        customerDto.setBirthDate(customer.getBirthDate());
        customerDto.setNickName(customer.getNickName());
        customerDto.setGender(customer.getGender());
        customerDto.setGenderDescription(customer.getGender().getTextForCurrentLan());
        return customerDto;
    }
}
