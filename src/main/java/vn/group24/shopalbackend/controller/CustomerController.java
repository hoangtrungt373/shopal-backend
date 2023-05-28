package vn.group24.shopalbackend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.group24.shopalbackend.controller.request.CustomerSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.request.CustomerSyncInfoRequest;
import vn.group24.shopalbackend.controller.response.admin.CustomerAllInfoDto;
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

    @PostMapping(value = "/current-customer/update", produces = {MediaType.ALL_VALUE, "application/json"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> updateCurrentCustomerInfo(@RequestPart(name = "dto") CustomerDto request, @RequestPart(name = "uploadAvatarUrl", required = false) MultipartFile uploadAvatarUrl) throws IOException {
        return ResponseEntity.ok().body(customerService.updateCustomerInfo(userUtils.getAuthenticateCustomer(), request, uploadAvatarUrl));
    }

    @PostMapping(value = "/current-customer/verify-new-email")
    public ResponseEntity<String> handleSendEmailVerifyEmailUpdate(@RequestBody String newEmail) {
        return ResponseEntity.ok().body(customerService.handleSendEmailVerifyEmailUpdate(userUtils.getAuthenticateCustomer(), newEmail));
    }

    @GetMapping("/current-enterprise/customer-membership/get-all")
    //TODO: get reactive check role
//    @PreAuthorize("hasRole('ENTERPRISE_MANAGER')")
    public ResponseEntity<List<CustomerMembershipDto>> getCustomerMembershipForCurrentEnterprise() {
        UserAccount userAccount = userUtils.getAuthenticateUser();
        return ResponseEntity.ok(customerService.getCustomerMembershipForEnterprise(userAccount));
    }

    @PostMapping("/customer-point-sync")
    //TODO: get reactive check role
//    @PreAuthorize("hasRole('ENTERPRISE_MANAGER')")
    public ResponseEntity<String> syncCustomerPoint(@RequestBody List<CustomerSyncInfoRequest> requests) {
        return ResponseEntity.ok(customerService.syncCustomerPoint(requests));
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

    @PostMapping("/get-all-info")
    //    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerAllInfoDto>> getCustomerAllInfoByCriteria(@RequestBody CustomerSearchCriteriaRequest request) {
        return ResponseEntity.ok(customerService.getCustomerAllInfoByCriteria(request));
    }
}
