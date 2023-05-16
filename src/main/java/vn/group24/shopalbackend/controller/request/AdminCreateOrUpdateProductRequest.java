package vn.group24.shopalbackend.controller.request;

import java.math.BigDecimal;
import java.time.LocalDate;
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
public class AdminCreateOrUpdateProductRequest {
    private Integer productId;
    private String productName;
    private String sku;
    private Integer quantityInStock;
    private LocalDate inputDate;
    private LocalDate expirationDate;
    private BigDecimal initialCash;
    private Integer catalogId;
    private ProductStatus productStatus;
    private ProductType productType;
    private List<String> imageUrls;
    //    private MultipartFile[] files;
    private String productDescriptionUrl;
}
