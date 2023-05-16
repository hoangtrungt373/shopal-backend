package vn.group24.shopalbackend.controller.response.admin;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class MembershipPointDto {
    private Integer enterpriseId;
    private String enterpriseName;
    private String enterpriseLogoUrl;
    private Integer membershipId;
    private BigDecimal availablePoint;
    private Integer totalBuy;
}
