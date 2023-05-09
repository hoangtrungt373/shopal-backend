package vn.group24.shopalbackend.controller.request;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.security.domain.enums.UserRole;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class ProductSearchCriteriaRequest extends AbstractSearchCriteria {
    private String keyword;
    private List<Integer> catalogIdList;
    private List<Integer> enterpriseIdList;
    private BigDecimal ratingMin;
    private BigDecimal ratingMax;
    private Integer isPolular;
    private Integer isLastest;
    private Integer isTopSales;
    private UserRole userRole;
}
