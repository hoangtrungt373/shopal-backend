package vn.group24.shopalbackend.domain.staging;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.AbstractAuditableEntity;

/**
 *
 * @author ttg
 */
@Entity
@Table(name = "STAG_CUSTOMER", schema = "staging")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "STAG_CUSTOMER_ID"))
public class StagCustomer extends AbstractAuditableEntity {

    @NotNull
    @Column(name = "ENTERPRISE_ID")
    private Integer enterpriseId;

    @NotNull
    @Column(name = "FULL_NAME")
    private String fullName;

    @NotNull
    @Column(name = "REGISTER_PHONE_NUMBER")
    private String registerPhoneNumber;

    @NotNull
    @Column(name = "REGISTER_EMAIL")
    private String registerEmail;

    @NotNull
    @Column(name = "INITIAL_POINT")
    private BigDecimal initialPoint;

    @NotNull
    @Column(name = "IMPORT_DATE")
    private LocalDateTime importDate;
}
