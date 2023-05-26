package vn.group24.shopalbackend.repository;

import java.util.List;

import vn.group24.shopalbackend.controller.request.ProductSearchCriteriaRequest;
import vn.group24.shopalbackend.domain.Product;

public interface ProductRepositoryCustom {
    Product getDetailById(Integer productId);

    List<Product> getByCriteria(ProductSearchCriteriaRequest criteria);

    Integer countByCriteria(ProductSearchCriteriaRequest criteria);
}
