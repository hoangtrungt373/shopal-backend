package vn.group24.shopalbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.group24.shopalbackend.controller.request.ModifyProductCartRequest;
import vn.group24.shopalbackend.controller.response.common.CartDto;
import vn.group24.shopalbackend.service.CartService;
import vn.group24.shopalbackend.util.UserUtils;

/**
 *
 * @author ttg
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserUtils userUtils;

    @GetMapping("/current-customer/get")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CartDto> getCurrentCustomerCart() {
        CartDto cart = cartService.getAllProductCartForCustomer(userUtils.getAuthenticateCustomer().getId());
        return ResponseEntity.ok().body(cart);
    }

    @PostMapping("/current-customer/update")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CartDto> addOrRemoveProductCartForCurrentCustomer(@RequestBody ModifyProductCartRequest request) {
        CartDto cart = cartService.addOrRemoveProductCartForCustomer(userUtils.getAuthenticateCustomer().getId(),
                request.getProductId(), request.getAmount(), request.getProductPointId());
        return ResponseEntity.ok().body(cart);
    }

}
