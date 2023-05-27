package vn.group24.shopalbackend.domain.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.EnterpriseProductSellingRequestStatus;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class EnterpriseProductSellingRequestAnn extends AbstractAnn {
    private Integer cooperationContractId;
    private BigDecimal cashPerPoint;
    private EnterpriseProductSellingRequestStatus enterpriseProductSellingRequestStatus;
    private String enterpriseProductSellingRequestStatusDescription;
}
