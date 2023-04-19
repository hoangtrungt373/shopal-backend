package vn.group24.shopalbackend.domain;

import java.math.BigDecimal;

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
import vn.group24.shopalbackend.domain.enums.ProductSource;


@Entity
@Table(name = "PRODUCT_POINT", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "PRODUCT_POINT_ID"))
public class ProductPoint extends AbstractStateAndAncestorManageableEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTERPRISE_ID")
    private Enterprise enterprise;

    @NotNull
    @Column(name = "SOURCE")
    @Enumerated(EnumType.STRING)
    private ProductSource source;

    @NotNull
    @Column(name = "INITIAL_CASH")
    private BigDecimal initialCash;

    @Column(name = "POINT_EXCHANGE")
    private BigDecimal pointExchange;

    @NotNull
    @Column(name = "UPDATE_DESCRIPTION")
    private String updateDescription;

    @NotNull
    @Column(name = "ACTIVE")
    private Boolean active;

    @Column(name = "POINT_NAME")
    private String pointName;
}