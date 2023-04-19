package vn.group24.shopalbackend.controller.response.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCatalogDto extends AbstractDto {
    private CatalogDto catalogDto;
    private ProductDto productDto;
}
