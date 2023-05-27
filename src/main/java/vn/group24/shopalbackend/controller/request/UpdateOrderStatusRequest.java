package vn.group24.shopalbackend.controller.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.OrderStatus;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class UpdateOrderStatusRequest {
    private Integer purchaseOrderId;
    private OrderStatus newOrderStatus;
    private LocalDateTime deliveryDate;
}
