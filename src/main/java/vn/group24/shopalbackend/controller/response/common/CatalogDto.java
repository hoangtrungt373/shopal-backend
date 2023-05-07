package vn.group24.shopalbackend.controller.response.common;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.CatalogStatus;
import vn.group24.shopalbackend.domain.enums.ProductTrendingState;

/**
 * @author ttg
 */
@Getter
@Setter
public class CatalogDto extends AbstractDto {
    private String catalogName;
    private String parentCatalogName;
    private Integer parentCatalogId;
    private String logoUrl;
    private Integer level;
    private CatalogStatus catalogStatus;
    private String catalogStatusDescription;
    private List<CatalogDto> childCatalogs = new ArrayList<>();
    private Integer totalProduct;
    private Integer totalSell;
    private ProductTrendingState productTrendingState;
}
