package vn.group24.shopalbackend.controller.response.customer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.common.AbstractDto;
import vn.group24.shopalbackend.controller.response.common.CatalogDto;
import vn.group24.shopalbackend.controller.response.common.ProductImageDto;
import vn.group24.shopalbackend.controller.response.common.ProductPointDto;
import vn.group24.shopalbackend.domain.enums.ProductStatus;

@Getter
@Setter
public class ProductDetailDto extends AbstractDto {
    private String productName;
    private String sku;
    private Integer quantityInStock;
    private String descriptionContentUrl;
    private ProductStatus productStatus;
    private String productStatusDescription;
    private BigDecimal rating;
    private Integer amountSold;
    private BigDecimal initialCash;
    private LocalDate inputDate;
    private LocalDate expirationDate;
    private List<ProductImageDto> imageUrls = new ArrayList<>();
    private List<ProductPointDto> exchangeAblePoints = new ArrayList<>();
    private List<CatalogDto> catalogs = new ArrayList<>();
}
