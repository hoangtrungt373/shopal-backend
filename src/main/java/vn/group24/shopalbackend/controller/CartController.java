package vn.group24.shopalbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.group24.shopalbackend.controller.request.UpdateProductCartRequest;
import vn.group24.shopalbackend.controller.response.common.CartDto;
import vn.group24.shopalbackend.service.CartService;

/**
 *
 * @author ttg
 */
@RestController
@RequestMapping("/api/cart")
public class CartController extends AbstractController {

    @Autowired
    private CartService cartService;

    @GetMapping("/current-customer/get")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CartDto> getCurrentCustomerCart() {
        CartDto cart = cartService.getAllProductCartForCustomer(userUtils.getAuthenticateCustomer());
        return ResponseEntity.ok().body(cart);
    }

    @PostMapping("/current-customer/update")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CartDto> updateProductCartsForCurrentCustomer(@RequestBody List<UpdateProductCartRequest> request) {
        CartDto cart = cartService.updateProductCartsForCustomer(userUtils.getAuthenticateCustomer(), request);
        return ResponseEntity.ok().body(cart);
    }

}
