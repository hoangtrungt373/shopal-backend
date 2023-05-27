package vn.group24.shopalbackend.controller.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.ContractStatus;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class CooperationContractSearchCriteriaRequest extends AbstractSearchCriteria {
    private LocalDate startDate;
    private LocalDate endDate;
    private ContractStatus contractStatus;
    private Integer enterpriseId;
}
