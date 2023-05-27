package vn.group24.shopalbackend.service;

import java.io.IOException;
import java.util.List;

import vn.group24.shopalbackend.controller.request.CustomerNewMembershipRequest;
import vn.group24.shopalbackend.controller.response.enterprise.CustomerRegisterDto;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.security.domain.UserAccount;

/**
 *
 * @author ttg
 */
public interface StagCustomerService {
    String handleRequestNewMembershipForCustomer(Customer customer, CustomerNewMembershipRequest createNewMembershipRequest);

    void importEnterpriseRegisterCustomerIntoStagCustomer(String dataFileUrl, Enterprise enterprise) throws IOException;

    List<CustomerRegisterDto> getCustomerRegisterForEnterprise(UserAccount userAccount);

    String importRegisterCustomers(List<CustomerRegisterDto> request, Enterprise enterprise);

}
