package vn.group24.shopalbackend.controller.request;

import java.time.LocalDate;
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
    private LocalDate orderDateFrom;
    private LocalDate orderDateTo;
    private List<Integer> enterpriseIds;
    private OrderStatus orderStatus;
}
