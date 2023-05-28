package vn.group24.shopalbackend.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import vn.group24.shopalbackend.controller.request.CustomerSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.request.CustomerSyncInfoRequest;
import vn.group24.shopalbackend.controller.response.admin.CustomerAllInfoDto;
import vn.group24.shopalbackend.controller.response.common.CustomerDto;
import vn.group24.shopalbackend.controller.response.enterprise.CustomerMembershipDto;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.security.domain.UserAccount;

/**
 * @author ttg
 */
public interface CustomerService {

    CustomerDto getCustomerInfo(UserAccount userAccount);

    List<CustomerAllInfoDto> getCustomerAllInfoByCriteria(CustomerSearchCriteriaRequest request);

    String updateCustomerInfo(Customer customer, CustomerDto request, MultipartFile uploadAvatarUrl) throws IOException;

    String handleSendEmailVerifyEmailUpdate(Customer customer, String newEmail);

    List<CustomerMembershipDto> getCustomerMembershipForEnterprise(UserAccount userAccount);

    String syncCustomerPoint(List<CustomerSyncInfoRequest> customerSyncInfoRequests);
}
