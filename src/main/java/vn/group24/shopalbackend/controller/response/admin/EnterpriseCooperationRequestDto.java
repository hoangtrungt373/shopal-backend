package vn.group24.shopalbackend.controller.response.admin;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.common.AbstractDto;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.domain.enums.EnterpriseRegisterRequestStatus;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class EnterpriseCooperationRequestDto extends AbstractDto {
    private String fullName;
    private String position;
    private String workEmail;
    private String phoneNumber;
    private String enterpriseAddress;
    private String enterpriseName;
    private String enterpriseWebsite;
    private EnterpriseRegisterRequestStatus registerRequestStatus;
    private String registerRequestStatusDescription;
    private LocalDateTime registerDate;
    private LocalDateTime verificationDate;
    private EnterpriseDto enterprise;
}
