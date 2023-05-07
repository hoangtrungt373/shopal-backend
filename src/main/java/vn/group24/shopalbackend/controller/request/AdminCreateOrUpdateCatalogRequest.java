package vn.group24.shopalbackend.controller.request;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class AdminCreateOrUpdateCatalogRequest {
    private Integer catalogId;
    private String productType;
    private String productTypeDescriptionVN;
    private String productTypeDescriptionEN;
    private String logoUrl;
    private Integer parentCatalogId;
    private Integer level;
}
