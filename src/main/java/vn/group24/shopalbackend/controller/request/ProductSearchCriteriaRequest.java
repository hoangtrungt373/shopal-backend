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
public class ProductSearchCriteriaRequest extends AbstractSearchCriteria {
    private Integer productId;
    private String keyword;
    private String sku;
    private List<Integer> catalogIdList;
    private List<Integer> enterpriseIdList;
    private BigDecimal ratingMin;
    private BigDecimal ratingMax;
    private ProductType productType;
    private ProductStatus productStatus;
    private LocalDate inputDateFrom;
    private LocalDate inputDateTo;
    private LocalDate expirationDateFrom;
    private LocalDate expirationDateTo;
    private BigDecimal initialCashFrom;
    private BigDecimal initialCashTo;
    private Integer isPolular;
    private Integer isLastest;
    private Integer isTopSales;
}
