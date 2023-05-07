package vn.group24.shopalbackend.controller.response.enterprise;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.common.AbstractDto;
import vn.group24.shopalbackend.controller.response.common.CustomerDto;
import vn.group24.shopalbackend.controller.response.customer.PurchaseOrderDetailDto;
import vn.group24.shopalbackend.domain.enums.DeliveryStatus;
import vn.group24.shopalbackend.domain.enums.OrderStatus;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class EnterprisePurchaseOrderDto extends AbstractDto {
    private String customerContactEmail;
    private String customerFullName;
    private CustomerDto customer;
    private BigDecimal orderTotalPointExchange;
    private LocalDateTime orderDate;
    private DeliveryStatus deliveryStatus;
    private String deliveryStatusDescription;
    private OrderStatus orderStatus;
    private String orderStatusDescription;
    private LocalDateTime deliveryDate;
    private Integer orderTotalItems;
    private BigDecimal orderTotalCash;
    private List<PurchaseOrderDetailDto> purchaseProducts = new ArrayList<>();
}
