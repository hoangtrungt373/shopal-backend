package vn.group24.shopalbackend.domain;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.ProductType;


@Entity
@Table(name = "CATALOG", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "CATALOG_ID"))
public class Catalog extends AbstractAuditableEntity {

    @Column(name = "PRODUCT_TYPE")
    @Enumerated(EnumType.STRING)
    public ProductType productType;

    @NotNull
    @Column(name = "LOGO_URL")
    private String logoUrl;

    @NotNull
    @Column(name = "LEVEL")
    private Integer level;

    @OneToMany(mappedBy = "catalog", fetch = FetchType.LAZY)
    private Set<ProductCatalog> productCatalogs = new HashSet<>();
}
