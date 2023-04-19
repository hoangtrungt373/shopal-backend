package vn.group24.shopalbackend.controller.response.specific;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.common.AbstractDto;
import vn.group24.shopalbackend.controller.response.common.CatalogDto;
import vn.group24.shopalbackend.controller.response.common.ProductImageDto;
import vn.group24.shopalbackend.controller.response.common.ProductPointDto;

@Getter
@Setter
public class ProductDetailDto extends AbstractDto {
    private String productName;
    private String sku;
    private Integer quantityInStock;
    private String descriptionContentUrl;
    private Boolean active;
    private List<ProductImageDto> imageUrls = new ArrayList<>();
    private List<ProductPointDto> points = new ArrayList<>();
    private List<CatalogDto> catalogs = new ArrayList<>();
}
