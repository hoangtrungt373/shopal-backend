package vn.group24.shopalbackend.controller.request;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class PurchaseOrderCancelRequest {
    private Integer purchaseOrderId;
    private String cancelCode;
    private Integer customerId;
}
