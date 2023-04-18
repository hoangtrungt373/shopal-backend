package vn.group24.shopalbackend.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "COOPERATION_CONTRACT", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "COOPERATION_CONTRACT_ID"))
public class CooperationContract extends AbstractAuditableEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTERPRISE_ID")
    private Enterprise enterprise;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNTING_ID")
    private Accounting accounting;

    @NotNull
    @Column(name = "START_DATE")
    private LocalDate startDate;

    @NotNull
    @Column(name = "END_DATE")
    private LocalDate endDate;

    @NotNull
    @Column(name = "POINT_NAME")
    private String pointName;

    @NotNull
    @Column(name = "COMMISSION_RATE")
    private BigDecimal commissionRate;

    @NotNull
    @Column(name = "CASH_PER_POINT")
    private BigDecimal cashPerPoint;

    @NotNull
    @Column(name = "ACTIVE")
    private Boolean active;
}
