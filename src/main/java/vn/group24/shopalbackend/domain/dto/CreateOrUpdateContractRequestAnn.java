package vn.group24.shopalbackend.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.ContractChangeRequestStatus;
import vn.group24.shopalbackend.domain.enums.ContractStatus;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class CreateOrUpdateContractRequestAnn extends AbstractAnn {
    private Integer cooperationContractId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate updateEndDate;
    private BigDecimal commissionRate;
    private BigDecimal updateCommissionRate;
    private BigDecimal cashPerPoint;
    private BigDecimal updateCashPerPoint;
    private ContractStatus contractStatus;
    private ContractStatus updateContractStatus;
    private String contractStatusDescription;
    private String updateContractStatusDescription;
    private Boolean isEdit;
    private ContractChangeRequestStatus contractChangeRequestStatus;
    private String cancelReason;
}
