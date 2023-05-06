package vn.group24.shopalbackend.controller.request;

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
}
