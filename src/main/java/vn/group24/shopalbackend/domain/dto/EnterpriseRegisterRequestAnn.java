package vn.group24.shopalbackend.domain.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.EnterpriseRegisterRequestStatus;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class EnterpriseRegisterRequestAnn extends AbstractAnn {
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
    private String taxId;
    private String tempPasswordGenerate;
}
