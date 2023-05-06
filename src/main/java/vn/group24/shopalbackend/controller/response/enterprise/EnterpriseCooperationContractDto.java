package vn.group24.shopalbackend.controller.response.enterprise;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.common.AbstractDto;
import vn.group24.shopalbackend.domain.enums.ContractStatus;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class EnterpriseCooperationContractDto extends AbstractDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal commissionRate;
    private BigDecimal cashPerPoint;
    private String updateDescription;
    private ContractStatus contractStatus;
    private String contractStatusDescription;
}
