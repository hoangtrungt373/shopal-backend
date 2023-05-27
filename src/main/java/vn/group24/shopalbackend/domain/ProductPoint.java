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
@Table(name = "PRODUCT_POINT", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "PRODUCT_POINT_ID"))
public class ProductPoint extends AbstractGenerationEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTERPRISE_ID")
    private Enterprise enterprise;

    @Column(name = "POINT_EXCHANGE")
    private BigDecimal pointExchange;

    @NotNull
    @Column(name = "ACTIVE")
    private Boolean active;

    public ProductPoint copy(Product generation) {
        ProductPoint copy = new ProductPoint();
        copy.setEnterprise(getEnterprise());
        copy.setPointExchange(getPointExchange());
        copy.setActive(getActive());
        copy.setProduct(generation);
        return copy;
    }

    public ProductPoint copy() {
        ProductPoint copy = new ProductPoint();
        copy.setProduct(getProduct());
        copy.setEnterprise(getEnterprise());
        copy.setPointExchange(getPointExchange());
        copy.setActive(getActive());
        copy.setOriginId(getOriginId());
        return copy;
    }
}
