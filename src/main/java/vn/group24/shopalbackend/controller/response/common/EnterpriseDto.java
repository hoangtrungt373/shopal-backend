package vn.group24.shopalbackend.controller.response.common;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.enterprise.EnterpriseCooperationContractDto;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class EnterpriseDto extends AbstractDto {
    private String enterpriseName;
    private String phoneNumber;
    private String address;
    private String websiteUrl;
    private String logoUrl;
    private LocalDate joinDate;
    private String contactEmail;
    private String taxId;
    private EnterpriseCooperationContractDto contract;
}
