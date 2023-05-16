package vn.group24.shopalbackend.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.ProductReviewType;


@Entity
@Table(name = "PRODUCT_REVIEW", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "PRODUCT_REVIEW_ID"))
public class ProductReview extends AbstractAuditableEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PURCHASE_ORDER_DETAIL_ID")
    private PurchaseOrderDetail purchaseOrderDetail;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Column(name = "RATING")
    private BigDecimal rating;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "IMAGE_URLS")
    private String imageUrls;

    @NotNull
    @Column(name = "AMOUNT_LIKE")
    private Integer amountLike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_REVIEW_ID")
    private ProductReview parentReview;

    @NotNull
    @Column(name = "REVIEW_TYPE")
    @Enumerated(EnumType.STRING)
    private ProductReviewType reviewType;

    @NotNull
    @Column(name = "REVIEW_DATE")
    private LocalDateTime reviewDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentReview")
    private Set<ProductReview> childReviews = new HashSet<>();
}
