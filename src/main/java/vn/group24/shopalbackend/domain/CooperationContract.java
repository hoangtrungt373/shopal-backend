package vn.group24.shopalbackend.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.ContractStatus;


@Entity
@Table(name = "COOPERATION_CONTRACT", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "COOPERATION_CONTRACT_ID"))
public class CooperationContract extends AbstractGenerationEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTERPRISE_ID")
    private Enterprise enterprise;

    @NotNull
    @Column(name = "START_DATE")
    private LocalDate startDate;

    @NotNull
    @Column(name = "END_DATE")
    private LocalDate endDate;

    @NotNull
    @Column(name = "COMMISSION_RATE")
    private BigDecimal commissionRate;

    @NotNull
    @Column(name = "CASH_PER_POINT")
    private BigDecimal cashPerPoint;

    @NotNull
    @Column(name = "CONTRACT_STATUS")
    @Enumerated(EnumType.STRING)
    private ContractStatus contractStatus;

    public CooperationContract copy() {
        CooperationContract copy = new CooperationContract();
        copy.setEnterprise(getEnterprise());
        copy.setStartDate(getStartDate());
        copy.setEndDate(getEndDate());
        copy.setCommissionRate(getCommissionRate());
        copy.setCashPerPoint(getCashPerPoint());
        copy.setContractStatus(getContractStatus());
        copy.setOriginId(getOriginId());
        return copy;
    }
}
