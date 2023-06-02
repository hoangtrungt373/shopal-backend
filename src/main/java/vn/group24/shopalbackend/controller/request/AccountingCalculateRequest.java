package vn.group24.shopalbackend.controller.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class AccountingCalculateRequest extends AbstractSearchCriteria {
    private LocalDate startDate;
    private LocalDate endDate;
}
