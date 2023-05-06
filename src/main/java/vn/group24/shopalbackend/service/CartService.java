package vn.group24.shopalbackend.service;

import java.util.List;

import vn.group24.shopalbackend.controller.request.UpdateProductCartRequest;
import vn.group24.shopalbackend.controller.response.common.CartDto;
import vn.group24.shopalbackend.domain.Customer;

/**
 *
 * @author ttg
 */
public interface CartService {

    CartDto updateProductCartsForCustomer(Customer customer, List<UpdateProductCartRequest> updateProductCartRequests);

    CartDto getAllProductCartForCustomer(Customer customer);
}
