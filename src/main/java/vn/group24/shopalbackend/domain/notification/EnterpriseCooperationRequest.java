package vn.group24.shopalbackend.domain.notification;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.AbstractAuditableEntity;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.enums.EnterpriseRegisterRequestStatus;

/**
 *
 * @author ttg
 */
@Entity
@Table(name = "ENTERPRISE_COOPERATION_REQUEST", schema = "notification")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "ENTERPRISE_COOPERATION_REQUEST_ID"))
public class EnterpriseCooperationRequest extends AbstractAuditableEntity {

    @NotNull
    @Column(name = "FULLNAME")
    private String fullName;

    @NotNull
    @Column(name = "POSITION")
    private String position;

    @NotNull
    @Column(name = "WORK_EMAIL")
    private String workEmail;

    @NotNull
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @NotNull
    @Column(name = "ENTERPRISE_ADDRESS")
    private String enterpriseAddress;

    @NotNull
    @Column(name = "ENTERPRISE_NAME")
    private String enterpriseName;

    @NotNull
    @Column(name = "ENTERPRISE_WEBSITE")
    private String enterpriseWebsite;

    @NotNull
    @Column(name = "REGISTER_REQUEST_STATUS")
    @Enumerated(EnumType.STRING)
    private EnterpriseRegisterRequestStatus registerRequestStatus;

    @NotNull
    @Column(name = "REGISTER_DATE")
    private LocalDateTime registerDate;

    @Column(name = "VERIFICATION_DATE")
    private LocalDateTime verificationDate;

    @Column(name = "TEMP_PASSWORD_GENERATE")
    private String tempPasswordGenerate;

    @Column(name = "ENTERPRISE_ID")
    private Integer enterpriseId;

    @Transient
    private Enterprise enterprise;
}
