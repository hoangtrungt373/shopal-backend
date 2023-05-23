package vn.group24.shopalbackend.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.ProductStatus;
import vn.group24.shopalbackend.domain.enums.ProductType;


@Entity
@Table(name = "PRODUCT", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "PRODUCT_ID"))
public class Product extends AbstractStateAndAncestorManageableEntity {

    @NotNull
    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "SKU")
    private String sku;

    @NotNull
    @Column(name = "QUANTITY_IN_STOCK")
    private Integer quantityInStock;

    @Column(name = "PRODUCT_DESCRIPTION_URL")
    private String productDescriptionUrl;

    @NotNull
    @Column(name = "PRODUCT_STATUS")
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @NotNull
    @Column(name = "RATING")
    private BigDecimal rating;

    @NotNull
    @Column(name = "INPUT_DATE")
    private LocalDate inputDate;

    @NotNull
    @Column(name = "EXPIRATION_DATE")
    private LocalDate expirationDate;

    @NotNull
    @Column(name = "INITIAL_CASH")
    private BigDecimal initialCash;

    @NotNull
    @Column(name = "TOTAL_SOLD")
    private Integer totalSold;

    @NotNull
    @Column(name = "TOTAL_REVIEW")
    private Integer totalReview;

    @NotNull
    @Column(name = "PRODUCT_TYPE")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @NotNull
    @Column(name = "UPDATE_DESCRIPTION")
    private String updateDescription;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProductGallery> productGalleries = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProductPoint> productPoints = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProductCatalog> productCatalogs = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProductReview> productReviews = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PurchaseOrderDetail> purchaseOrderDetails = new HashSet<>();


    public void addProductGallery(ProductGallery productGallery) {
        this.productGalleries.add(productGallery);
        productGallery.setProduct(this);
    }

    public void addProductCatalog(ProductCatalog productCatalog) {
        this.productCatalogs.add(productCatalog);
        productCatalog.setProduct(this);
    }

    public void addProductReview(ProductReview productReview) {
        this.productReviews.add(productReview);
        productReview.setProduct(this);
    }

    public void addProductPoint(ProductPoint productPoint) {
        this.productPoints.add(productPoint);
        productPoint.setProduct(this);
    }

    public Product copy(Product product) {
        Product copy = new Product();
        copy.setProductName(product.getProductName());
        copy.setSku(product.getSku());
        copy.setQuantityInStock(product.getQuantityInStock());
        copy.setProductDescriptionUrl(product.getProductDescriptionUrl());
        copy.setProductStatus(product.getProductStatus());
        copy.setRating(product.getRating());
        copy.setInputDate(product.getInputDate());
        copy.setExpirationDate(product.getExpirationDate());
        copy.setInitialCash(product.getInitialCash());
        copy.setTotalSold(product.getTotalSold());
        copy.setTotalReview(product.getTotalReview());
        copy.setProductType(product.getProductType());
        copy.setOriginId(getOriginId());
        product.getProductGalleries().forEach(pg -> copy.addProductGallery(pg.copy(pg)));
        product.getProductCatalogs().forEach(pc -> copy.addProductCatalog(pc.copy(pc)));
        product.getProductPoints().forEach(pp -> copy.addProductPoint(pp.copy(pp)));
        return copy;
    }
}
