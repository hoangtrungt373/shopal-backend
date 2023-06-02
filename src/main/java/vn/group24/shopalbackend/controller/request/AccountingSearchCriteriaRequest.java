package vn.group24.shopalbackend.controller.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.PaymentStatus;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class AccountingSearchCriteriaRequest extends AbstractSearchCriteria {
    private Integer enterpriseId;
    private LocalDate startDate;
    private LocalDate endDate;
    private PaymentStatus paymentStatus;
}
