package vn.group24.shopalbackend.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import vn.group24.shopalbackend.controller.request.AdminCreateOrUpdateProductRequest;
import vn.group24.shopalbackend.controller.request.CustomerProductReviewRequest;
import vn.group24.shopalbackend.controller.request.ProductSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.admin.CreateOrUpdateProductResponse;
import vn.group24.shopalbackend.controller.response.common.ProductDto;
import vn.group24.shopalbackend.controller.response.customer.ProductDetailDto;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.dto.EnterpriseProductSellingRequestAnn;

/**
 * @author ttg
 */
public interface ProductService {

    ProductDetailDto getProductDetail(Integer productId);

    List<ProductDto> getProductByCriteria(ProductSearchCriteriaRequest criteria);

    Integer countProductByCriteria(ProductSearchCriteriaRequest criteria);

    String handleRequestSellingProductForEnterprise(Enterprise enterprise, Integer productId);

    String handleAcceptRequestSellingProduct(EnterpriseProductSellingRequestAnn enterpriseProductSellingRequestAnn);

    List<EnterpriseProductSellingRequestAnn> getAllEnterpriseProductSellingRequestAnn();

    String handleRequestCancellingProductForEnterprise(Enterprise enterprise, Integer productId);

    CreateOrUpdateProductResponse createOrUpdateProduct(AdminCreateOrUpdateProductRequest request, MultipartFile[] images) throws IOException;

    String addPurchaseOrderProductReviewByCustomer(Customer customer, CustomerProductReviewRequest request, MultipartFile[] images) throws IOException;

    String nextProductSku();
}
