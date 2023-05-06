package vn.group24.shopalbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.group24.shopalbackend.controller.response.common.CustomerDto;
import vn.group24.shopalbackend.controller.response.enterprise.CustomerMembershipDto;
import vn.group24.shopalbackend.controller.response.enterprise.CustomerRegisterDto;
import vn.group24.shopalbackend.security.domain.UserAccount;
import vn.group24.shopalbackend.service.CustomerService;
import vn.group24.shopalbackend.service.StagCustomerService;

/**
 *
 * @author ttg
 */

@RestController
@RequestMapping("/api/customer")
public class CustomerController extends AbstractController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private StagCustomerService stagCustomerService;

    @GetMapping("/current-customer/info")
    public ResponseEntity<CustomerDto> getCurrentCustomerInfo() {
        UserAccount userAccount = userUtils.getAuthenticateUser();
        return ResponseEntity.ok(customerService.getCustomerInfo(userAccount));
    }

    @GetMapping("/current-enterprise/customer-membership/get-all")
    //TODO: get reactive check role
//    @PreAuthorize("hasRole('ENTERPRISE_MANAGER')")
    public ResponseEntity<List<CustomerMembershipDto>> getCustomerMembershipForCurrentEnterprise() {
        UserAccount userAccount = userUtils.getAuthenticateUser();
        return ResponseEntity.ok(customerService.getCustomerMembershipForEnterprise(userAccount));
    }

    @GetMapping("/current-enterprise/customer-register/get-all")
//    @PreAuthorize("hasRole('ENTERPRISE_MANAGER')")
    public ResponseEntity<List<CustomerRegisterDto>> getCustomerRegisterForCurrentEnterprise() {
        UserAccount userAccount = userUtils.getAuthenticateUser();
        return ResponseEntity.ok(stagCustomerService.getCustomerRegisterForEnterprise(userAccount));
    }

    @PostMapping("/current-enterprise/customer-register/import")
//    @PreAuthorize("hasRole('ENTERPRISE_MANAGER')")
    public ResponseEntity<CustomerDto> importCustomerRegisterForCurrentEnterprise() {
        UserAccount userAccount = userUtils.getAuthenticateUser();
        return ResponseEntity.ok(customerService.getCustomerInfo(userAccount));
    }
}
