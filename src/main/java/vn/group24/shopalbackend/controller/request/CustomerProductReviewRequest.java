package vn.group24.shopalbackend.controller.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class CustomerProductReviewRequest {
    private Integer purchaseOrderDetailId;
    private BigDecimal rating;
    private String content;
}
