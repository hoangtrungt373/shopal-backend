package vn.group24.shopalbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.group24.shopalbackend.controller.response.specific.ProductDetailDto;
import vn.group24.shopalbackend.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/get-detail/{productId}")
    public ResponseEntity<ProductDetailDto> getProductDetail(@PathVariable Integer productId) {
        ProductDetailDto productDetailDto = productService.getProductDetail(productId);
        return ResponseEntity.ok().body(productDetailDto);
    }
}
