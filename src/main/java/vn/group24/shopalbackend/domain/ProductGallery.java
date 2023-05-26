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
@Table(name = "PRODUCT_GALLERY", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "PRODUCT_GALLERY_ID"))
public class ProductGallery extends AbstractGenerationEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @NotNull
    @Column(name = "FILE_URL")
    private String fileUrl;

    @NotNull
    @Column(name = "IS_MAIN_FILE")
    private Boolean isMainFile;

    public ProductGallery copy(Product product) {
        ProductGallery copy = new ProductGallery();
        copy.setFileUrl(getFileUrl());
        copy.setIsMainFile(getIsMainFile());
        copy.setProduct(product);
        return copy;
    }
}
