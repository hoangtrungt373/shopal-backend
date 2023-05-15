package vn.group24.shopalbackend.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
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
import vn.group24.shopalbackend.domain.enums.DeliveryStatus;
import vn.group24.shopalbackend.domain.enums.OrderStatus;


@Entity
@Table(name = "PURCHASE_ORDER", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "PURCHASE_ORDER_ID"))
public class PurchaseOrder extends AbstractAuditableEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTERPRISE_ID")
    private Enterprise enterprise;

    @NotNull
    @Column(name = "ORDER_TOTAL_POINT_EXHANGE")
    private BigDecimal orderTotalPointExchange;

    @NotNull
    @Column(name = "ORDER_TOTAL_CASH")
    private BigDecimal orderTotalCash;

    @NotNull
    @Column(name = "ORDER_DATE")
    private LocalDateTime orderDate;

    @NotNull
    @Column(name = "DELIVERY_STATUS")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @NotNull
    @Column(name = "ORDER_STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "DELIVERY_DATE")
    private LocalDateTime deliveryDate;

    @Column(name = "CANCEL_REASON")
    private String cancelReason;

    @Column(name = "CANCEL_DATE")
    private LocalDateTime cancelDate;

    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PurchaseOrderDetail> purchaseOrderDetails = new HashSet<>();

    public void addPurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail) {
        purchaseOrderDetails.add(purchaseOrderDetail);
        purchaseOrderDetail.setPurchaseOrder(this);
    }
}
