package vn.group24.shopalbackend.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.common.AbstractDto;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.domain.enums.ContractStatus;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class CooperationContractDto extends AbstractDto {
    private EnterpriseDto enterprise;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal commissionRate;
    private BigDecimal cashPerPoint;
    private ContractStatus contractStatus;
    private String contractStatusDescription;
}
