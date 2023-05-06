package vn.group24.shopalbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.group24.shopalbackend.controller.response.customer.EnterpriseMembershipDto;
import vn.group24.shopalbackend.service.MembershipService;

/**
 *
 * @author ttg
 */
@RestController
@RequestMapping("/api/membership")
public class MembershipController extends AbstractController {

    @Autowired
    private MembershipService membershipService;

    @GetMapping("/current-customer/enterprise-mambership/get-all")
    public ResponseEntity<List<EnterpriseMembershipDto>> getEnterpriseMembershipForCurrentCustomer() {
        return ResponseEntity.ok(membershipService.getEnterpriseMembershipForCustomer(userUtils.getAuthenticateCustomer()));
    }
}
