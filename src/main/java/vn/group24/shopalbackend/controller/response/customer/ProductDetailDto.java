package vn.group24.shopalbackend.controller.response.customer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.ProductReviewDto;
import vn.group24.shopalbackend.controller.response.common.AbstractStateDto;
import vn.group24.shopalbackend.controller.response.common.CatalogDto;
import vn.group24.shopalbackend.controller.response.common.ProductImageDto;
import vn.group24.shopalbackend.controller.response.common.ProductPointDto;
import vn.group24.shopalbackend.domain.enums.ProductStatus;
import vn.group24.shopalbackend.domain.enums.ProductType;

@Getter
@Setter
public class ProductDetailDto extends AbstractStateDto {
    private String productName;
    private String sku;
    private Integer quantityInStock;
    private String productDescriptionUrl;
    private ProductStatus productStatus;
    private String productStatusDescription;
    private BigDecimal rating;
    private Integer totalSold;
    private BigDecimal initialCash;
    private LocalDate inputDate;
    private LocalDate expirationDate;
    private Integer totalReview;
    private ProductType productType;
    private String productTypeDescription;
    private List<ProductImageDto> imageUrls = new ArrayList<>();
    private List<ProductPointDto> exchangeAblePoints = new ArrayList<>();
    private List<CatalogDto> catalogs = new ArrayList<>();
    private List<ProductReviewDto> reviews = new ArrayList<>();
}
