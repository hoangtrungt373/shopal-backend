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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.CatalogStatus;
import vn.group24.shopalbackend.domain.enums.ProductTrendingState;


@Entity
@Table(name = "CATALOG", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "CATALOG_ID"))
public class Catalog extends AbstractGenerationEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_CATALOG_ID")
    private Catalog parentCatalog;

    @NotNull
    @Column(name = "CATALOG_NAME")
    private String catalogName;

    @Column(name = "LOGO_URL")
    private String logoUrl;

    @NotNull
    @Column(name = "LEVEL")
    private Integer level;

    @NotNull
    @Column(name = "CATALOG_STATUS")
    @Enumerated(EnumType.STRING)
    private CatalogStatus catalogStatus;

    @Transient
    private Integer totalProduct;

    @Transient
    private Integer totalSell;

    @Transient
    private ProductTrendingState productTrendingState;

    @OneToMany(mappedBy = "catalog", fetch = FetchType.LAZY)
    private Set<ProductCatalog> productCatalogs = new HashSet<>();

    @OneToMany(mappedBy = "parentCatalog", fetch = FetchType.LAZY)
    private Set<Catalog> childCatalogs = new HashSet<>();
}
