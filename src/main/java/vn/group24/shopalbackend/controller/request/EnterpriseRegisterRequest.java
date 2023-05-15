package vn.group24.shopalbackend.controller.request;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.EnterpriseRegisterRequestStatus;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class EnterpriseRegisterRequest {
    private String fullName;
    private String position;
    private String workEmail;
    private String phoneNumber;
    private String enterpriseAddress;
    private String enterpriseName;
    private String enterpriseWebsite;
    private EnterpriseRegisterRequestStatus registerRequestStatus;
}
