package vn.group24.shopalbackend.controller.response.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageDto extends AbstractDto {
    private ProductDto product;
    private String imageUrl;
    private Boolean isMainImg;
}
