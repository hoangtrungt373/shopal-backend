package vn.group24.shopalbackend.controller.request;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.OrderStatus;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class PurchaseOrderSearchCriteriaRequest extends AbstractSearchCriteria {
    private Integer purchaseOrderId;
    private LocalDateTime orderDateFrom;
    private LocalDateTime orderDateTo;
    private String productSku;
    private Integer customerId;
    private String customerContactEmail;
    private String customerPhoneNumber;
    private List<Integer> enterpriseIds;
    private OrderStatus orderStatus;
}
