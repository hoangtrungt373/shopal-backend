package vn.group24.shopalbackend.controller.response.common;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.ProductStatus;
import vn.group24.shopalbackend.domain.enums.ProductType;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class ProductDto extends AbstractDto {
    private String productName;
    private String sku;
    private Integer quantityInStock;
    private String descriptionContentUrl;
    private String mainImgUrl;
    private String mainImgText;
    private BigDecimal rating;
    private ProductStatus productStatus;
    private String productStatusDescription;
    private Integer totalSold;
    private BigDecimal initialCash;
    private ProductType productType;
    private String productTypeDescription;
    private LocalDate inputDate;
    private LocalDate expirationDate;
    private List<ProductImageDto> imageUrls = new ArrayList<>();
    private List<ProductPointDto> exchangeAblePoints = new ArrayList<>();
    private List<CatalogDto> catalogs = new ArrayList<>();
}
