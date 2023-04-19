package vn.group24.shopalbackend.service;

import vn.group24.shopalbackend.controller.response.specific.ProductDetailDto;

/**
 * @author ttg
 */
public interface ProductService {

    ProductDetailDto getProductDetail(Integer productId);
}
