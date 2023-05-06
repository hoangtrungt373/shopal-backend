package vn.group24.shopalbackend.controller.response.customer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.common.AbstractDto;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.domain.enums.DeliveryStatus;
import vn.group24.shopalbackend.domain.enums.OrderStatus;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class CustomerPurchaseOrderDto extends AbstractDto {
    private EnterpriseDto enterprise;
    private BigDecimal orderTotalPointExchange;
    private LocalDateTime orderDate;
    private DeliveryStatus deliveryStatus;
    private String deliveryStatusDescription;
    private OrderStatus orderStatus;
    private String orderStatusDescription;
    private LocalDateTime deliveryDate;
    private List<PurchaseOrderDetailDto> purchaseProducts = new ArrayList<>();
}
