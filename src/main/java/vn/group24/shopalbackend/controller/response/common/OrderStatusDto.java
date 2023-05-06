package vn.group24.shopalbackend.controller.response.common;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.OrderStatus;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class OrderStatusDto {
    private OrderStatus orderStatus;
    private String orderStatusDescription;
}
