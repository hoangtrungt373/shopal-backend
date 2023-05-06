package vn.group24.shopalbackend.controller.response.customer;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.common.AbstractDto;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class EnterpriseMembershipDto extends AbstractDto {
    private EnterpriseDto enterprise;
    private String registerEmail;
    private String registerPhoneNumber;
    private BigDecimal availablePoint;
}
