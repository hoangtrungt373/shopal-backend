package vn.group24.shopalbackend.service;

import java.util.List;

import vn.group24.shopalbackend.controller.request.AdminCreateOrUpdateProductRequest;
import vn.group24.shopalbackend.controller.request.ProductSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.common.ProductDto;
import vn.group24.shopalbackend.controller.response.customer.ProductDetailDto;
import vn.group24.shopalbackend.domain.Enterprise;

/**
 * @author ttg
 */
public interface ProductService {

    ProductDetailDto getProductDetail(Integer productId);

    List<ProductDto> getProductByCriteria(ProductSearchCriteriaRequest criteria);

    String handleRequestSellingProductForEnterprise(Enterprise enterprise, Integer productId);

    String handleRequestCancellingProductForEnterprise(Enterprise enterprise, Integer productId);

    String createOrUpdateProduct(AdminCreateOrUpdateProductRequest request);
}
