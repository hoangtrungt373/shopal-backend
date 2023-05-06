package vn.group24.shopalbackend.service;

import java.util.List;

import vn.group24.shopalbackend.controller.response.customer.EnterpriseMembershipDto;
import vn.group24.shopalbackend.domain.Customer;

/**
 * @author ttg
 */
public interface MembershipService {

    List<EnterpriseMembershipDto> getEnterpriseMembershipForCustomer(Customer customer);
}
