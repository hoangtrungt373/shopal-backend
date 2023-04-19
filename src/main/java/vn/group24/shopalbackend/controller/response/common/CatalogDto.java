package vn.group24.shopalbackend.controller.response.common;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.ProductType;

/**
 * @author ttg
 */
@Getter
@Setter
public class CatalogDto extends AbstractDto {
    private ProductType productType;
    private String productTypeDescription;
    private String logoUrl;
    private Integer level;
}
