package vn.group24.shopalbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.group24.shopalbackend.controller.request.CustomerNewMembershipRequest;
import vn.group24.shopalbackend.controller.response.customer.EnterpriseMembershipDto;
import vn.group24.shopalbackend.service.MembershipService;
import vn.group24.shopalbackend.service.StagCustomerService;

/**
 *
 * @author ttg
 */
@RestController
@RequestMapping("/api/membership")
public class MembershipController extends AbstractController {

    @Autowired
    private MembershipService membershipService;
    @Autowired
    private StagCustomerService stagCustomerService;

    @GetMapping("/current-customer/enterprise-membership/get-all")
    public ResponseEntity<List<EnterpriseMembershipDto>> getEnterpriseMembershipForCurrentCustomer() {
        return ResponseEntity.ok(membershipService.getEnterpriseMembershipForCustomer(userUtils.getAuthenticateCustomer()));
    }

    @PostMapping("/current-customer/enterprise-membership/request-new")
    public ResponseEntity<String> handleRequestNewMembershipForCurrentCustomer(@RequestBody CustomerNewMembershipRequest request) {
        return ResponseEntity.ok(stagCustomerService.handleRequestNewMembershipForCustomer(userUtils.getAuthenticateCustomer(), request));
    }
}
