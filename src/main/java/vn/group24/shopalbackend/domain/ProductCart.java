package vn.group24.shopalbackend.domain;

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
@Table(name = "PRODUCT_CART", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "PRODUCT_CART_ID"))
public class ProductCart extends AbstractAuditableEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_POINT_ID")
    private ProductPoint productPoint;

    @NotNull
    @Column(name = "AMOUNT")
    private Integer amount;
}
