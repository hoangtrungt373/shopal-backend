package vn.group24.shopalbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.group24.shopalbackend.controller.request.AdminCreateOrUpdateProductRequest;
import vn.group24.shopalbackend.controller.request.ProductSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.common.ProductDto;
import vn.group24.shopalbackend.controller.response.customer.ProductDetailDto;
import vn.group24.shopalbackend.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController extends AbstractController {

    @Autowired
    private ProductService productService;

    @GetMapping("/get-detail/{productId}")
    public ResponseEntity<ProductDetailDto> getProductDetail(@PathVariable Integer productId) {
        ProductDetailDto productDetailDto = productService.getProductDetail(productId);
        return ResponseEntity.ok().body(productDetailDto);
    }

    @PostMapping("/get-by-criteria")
    public ResponseEntity<List<ProductDto>> getProductByCriteria(@RequestBody ProductSearchCriteriaRequest criteria) {
        List<ProductDto> productDtos = productService.getProductByCriteria(criteria);
        return ResponseEntity.ok().body(productDtos);
    }

    @GetMapping("/current-enterprise/request-selling/{productId}")
    public ResponseEntity<String> handleRequestSellingProductForCurrentEnterprise(@PathVariable Integer productId) {
        return ResponseEntity.ok().body(productService.handleRequestSellingProductForEnterprise(userUtils.getAuthenticateEnterprise(), productId));
    }

    @GetMapping("/current-enterprise/request-cancelling/{productId}")
    public ResponseEntity<String> handleRequestCancellingProductForCurrentEnterprise(@PathVariable Integer productId) {
        return ResponseEntity.ok().body(productService.handleRequestCancellingProductForEnterprise(userUtils.getAuthenticateEnterprise(), productId));
    }

    @PostMapping("/current-admin/create-or-update-product")
    public ResponseEntity<String> createOrUpdateProduct(@RequestBody AdminCreateOrUpdateProductRequest request) {
        return ResponseEntity.ok().body(productService.createOrUpdateProduct(request));
    }
}
