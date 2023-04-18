package vn.group24.shopalbackend.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.PaymentMethod;


@Entity
@Table(name = "ACCOUNTING", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "ACCOUNTING_ID"))
public class Accounting extends AbstractAuditableEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTERPRISE_ID")
    private Enterprise enterprise;

    @NotNull
    @Column(name = "START_DATE")
    private LocalDate startDate;

    @NotNull
    @Column(name = "START_DATE")
    private LocalDate endDate;

    @NotNull
    @Column(name = "TOTAL_INCOME")
    private BigDecimal totalIncome;

    @NotNull
    @Column(name = "TOTAL_COMMISSION")
    private BigDecimal totalCommission;

    @NotNull
    @Column(name = "ALREADY_PAID")
    private Boolean alreadyPaid;

    @Column(name = "PAYMENT_DATE")
    private LocalDateTime paymentDate;

    @Column(name = "PAYMENT_METHOD")
    @Enumerated(EnumType.STRING)
    public PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "accounting", fetch = FetchType.LAZY)
    private Set<CooperationContract> cooperationContracts = new HashSet<>();
}
