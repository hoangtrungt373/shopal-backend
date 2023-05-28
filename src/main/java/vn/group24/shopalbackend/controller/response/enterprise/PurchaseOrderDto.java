package vn.group24.shopalbackend.controller.response.enterprise;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.common.AbstractDto;
import vn.group24.shopalbackend.controller.response.common.CustomerDto;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.domain.enums.DeliveryStatus;
import vn.group24.shopalbackend.domain.enums.OrderStatus;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class PurchaseOrderDto extends AbstractDto {
    private CustomerDto customer;
    private EnterpriseDto enterprise;
    private BigDecimal orderTotalPointExchange;
    private LocalDateTime orderDate;
    private LocalDateTime cancelDate;
    private DeliveryStatus deliveryStatus;
    private String deliveryStatusDescription;
    private OrderStatus orderStatus;
    private String orderStatusDescription;
    private LocalDateTime deliveryDate;
    private Integer orderTotalItems;
    private BigDecimal orderTotalCash;
    private String cancelCode;
    private List<PurchaseOrderDetailDto> purchaseProducts = new ArrayList<>();
}
