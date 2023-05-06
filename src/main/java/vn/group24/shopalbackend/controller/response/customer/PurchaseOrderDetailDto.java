package vn.group24.shopalbackend.controller.response.customer;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.common.AbstractDto;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class PurchaseOrderDetailDto extends AbstractDto {
    private Integer purchaseOrderDetailId;
    private Integer productId;
    private String productName;
    private String sku;
    private String mainImgUrl;
    private String mainImgText;
    private BigDecimal pointExchange;
    private Integer amount;
    private BigDecimal totalPointExchange;
}
