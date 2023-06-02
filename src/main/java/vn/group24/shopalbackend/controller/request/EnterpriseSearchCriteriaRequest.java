package vn.group24.shopalbackend.controller.request;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class EnterpriseSearchCriteriaRequest extends AbstractSearchCriteria {
    private String phoneNumber;
    private String address;
    private String contactEmail;
    private String taxId;
}
