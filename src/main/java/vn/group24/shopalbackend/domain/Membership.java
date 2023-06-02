package vn.group24.shopalbackend.domain;

import java.math.BigDecimal;

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
@Table(name = "MEMBERSHIP", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "MEMBERSHIP_ID"))
public class Membership extends AbstractGenerationEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTERPRISE_ID")
    private Enterprise enterprise;

    @NotNull
    @Column(name = "AVAILABLE_POINT")
    private BigDecimal availablePoint;

    @Column(name = "REGISTER_PHONE_NUMBER")
    private String registerPhoneNumber;

    @Column(name = "REGISTER_EMAIL")
    private String registerEmail;

    @NotNull
    @Column(name = "TOTAL_BUY")
    private Integer totalBuy;
}
