package vn.group24.shopalbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ttg
 */

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping("/account")
    public ResponseEntity<String> getCustomerAccountInfo() {
        return ResponseEntity.ok("Info here");
    }
}
