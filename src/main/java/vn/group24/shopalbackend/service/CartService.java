package vn.group24.shopalbackend.service;

import vn.group24.shopalbackend.controller.response.common.CartDto;

/**
 *
 * @author ttg
 */
public interface CartService {

    CartDto addOrRemoveProductCartForCustomer(Integer customerId, Integer productId, Integer amount, Integer productPointId);

    CartDto getAllProductCartForCustomer(Integer customerId);
}
