package vn.group24.shopalbackend.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
import vn.group24.shopalbackend.domain.enums.TypeMovement;


@Entity
@Table(name = "CUSTOMER_POINT_MOVEMENT", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "CUSTOMER_POINT_MOVEMENT_ID"))
public class CustomerPointMovement extends AbstractGenerationEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PURCHASE_ORDER_ID")
    private PurchaseOrder purchaseOrder;

    @NotNull
    @Column(name = "VALUE")
    private BigDecimal value;

    @NotNull
    @Column(name = "PAY")
    private BigDecimal pay;

    @NotNull
    @Column(name = "DATE_MOVEMENT")
    private LocalDateTime dateMovement;

    @NotNull
    @Column(name = "TYPE_MOVEMENT")
    @Enumerated(EnumType.STRING)
    private TypeMovement typeMovement;
}
