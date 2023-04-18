package vn.group24.shopalbackend.controller.response;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.Catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductDetailDto {
    private Catalog catalog;
    private String productName;
    private String sku;
    private BigDecimal price;
    private Integer quantityInStock;
    private Integer descriptionContentUrl;
    private Boolean active;
    private List<ProductImageDto> productImageDtos = new ArrayList<>();
}
