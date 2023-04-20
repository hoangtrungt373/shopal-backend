package vn.group24.shopalbackend.repository;

import vn.group24.shopalbackend.domain.Product;

public interface ProductRepositoryCustom {
    Product getProductDetailById(Integer productId);
}
