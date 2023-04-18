package vn.group24.shopalbackend.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageDto {
    private String imageUrl;
    private Boolean isMainImg;
}
