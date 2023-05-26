package vn.group24.shopalbackend.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "PURCHASE_ORDER_DETAIL", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "PURCHASE_ORDER_DETAIL_ID"))
public class PurchaseOrderDetail extends AbstractGenerationEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PURCHASE_ORDER_ID")
    private PurchaseOrder purchaseOrder;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_POINT_ID")
    private ProductPoint productPoint;

    @NotNull
    @Column(name = "POINT_EXCHANGE")
    private BigDecimal pointExchange;

    @NotNull
    @Column(name = "AMOUNT")
    private Integer amount;

    @NotNull
    @Column(name = "TOTAL_POINT_EXCHANGE")
    private BigDecimal totalPointExchange;

    @NotNull
    @Column(name = "TOTAL_CASH")
    private BigDecimal totalCash;

    @Column(name = "CONTACT_EMAIL")
    private String contactEmail;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrderDetail", cascade = CascadeType.ALL)
    private Set<ProductReview> productReviews = new HashSet<>();

    public PurchaseOrderDetail copy(Product generation, ProductPoint newProductPointGe) {
        PurchaseOrderDetail copy = new PurchaseOrderDetail();
        copy.setPurchaseOrder(getPurchaseOrder());
        copy.setProduct(generation);
        copy.setProductPoint(newProductPointGe);
        copy.setPointExchange(getPointExchange());
        copy.setAmount(getAmount());
        copy.setTotalPointExchange(getTotalPointExchange());
        copy.setTotalCash(getTotalCash());
        copy.setContactEmail(getContactEmail());
        copy.setProductReviews(getProductReviews().stream().map(pr -> pr.copy(generation, copy)).collect(Collectors.toSet()));
        return copy;
    }
}
