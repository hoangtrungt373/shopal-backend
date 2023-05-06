package vn.group24.shopalbackend.service;

import java.util.List;

import vn.group24.shopalbackend.controller.response.common.CustomerDto;
import vn.group24.shopalbackend.controller.response.enterprise.CustomerMembershipDto;
import vn.group24.shopalbackend.security.domain.UserAccount;

/**
 * @author ttg
 */
public interface CustomerService {

    CustomerDto getCustomerInfo(UserAccount userAccount);

    List<CustomerMembershipDto> getCustomerMembershipForEnterprise(UserAccount userAccount);
}
