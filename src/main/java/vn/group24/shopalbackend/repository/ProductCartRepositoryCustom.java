package vn.group24.shopalbackend.repository;

import java.util.List;

import vn.group24.shopalbackend.domain.ProductCart;

/**
 *
 * @author ttg
 */
public interface ProductCartRepositoryCustom {
    List<ProductCart> getAllProductCartByCustomerId(Integer customerId);
}
