package vn.group24.shopalbackend.controller.request;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class CustomerPurchaseOrderCancelRequest {
    private Integer purchaseOrderId;
    private String cancelReason;
}
