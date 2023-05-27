package vn.group24.shopalbackend.controller.response.enterprise;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.common.AbstractDto;
import vn.group24.shopalbackend.controller.response.common.ProductDto;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class PurchaseOrderDetailDto extends AbstractDto {
    private ProductDto product;
    private Integer productId;
    private BigDecimal pointExchange;
    private Integer amount;
    private BigDecimal totalPointExchange;
    private BigDecimal totalCash;
    private Boolean isReview;
}
