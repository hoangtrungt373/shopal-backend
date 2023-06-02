package vn.group24.shopalbackend.controller.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class CatalogSearchCriteriaRequest extends AbstractSearchCriteria {
    private String productKeyword;
    private String enterpriseKeyword;
    private List<Integer> enterpriseIds;
    private Integer parentCatalogId;
    private Integer childCatalogId;
}
