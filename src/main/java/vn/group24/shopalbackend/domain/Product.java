package vn.group24.shopalbackend.domain;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "PRODUCT", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "PRODUCT_ID"))
public class Product extends AbstractAuditableEntity {

    @NotNull
    @Column(name = "PRODUCT_NAME")
    private String productName;

    @NotNull
    @Column(name = "SKU")
    private String sku;

    @NotNull
    @Column(name = "QUANTITY_IN_STOCK")
    private Integer quantityInStock;

    @Column(name = "DESCRIPTION_CONTENT_URL")
    private String descriptionContentUrl;

    @NotNull
    @Column(name = "ACTIVE")
    private Boolean active;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ProductImage> productImages = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ProductPoint> productPoints = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ProductCatalog> productCatalogs = new HashSet<>();
}
