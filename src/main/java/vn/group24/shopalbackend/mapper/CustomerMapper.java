package vn.group24.shopalbackend.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.common.CustomerDto;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.multilingual.GenderLan;
import vn.group24.shopalbackend.util.LanguageUtils;

/**
 *
 * @author ttg
 */
@Component
public class CustomerMapper {

    @Autowired
    private LanguageUtils languageUtils;

    public CustomerDto mapToCustomerDto(Customer entity) {
        CustomerDto dto = new CustomerDto();
        dto.setContactEmail(entity.getContactEmail());
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setAddress(entity.getAddress());
        dto.setAvatarUrl(entity.getAvatarUrl());
        dto.setBirthDate(entity.getBirthDate());
        dto.setGender(entity.getGender());
        dto.setGenderDescription(languageUtils.getEnumDescription(entity.getGender(), GenderLan.TABLE_NAME));
        return dto;
    }
}
