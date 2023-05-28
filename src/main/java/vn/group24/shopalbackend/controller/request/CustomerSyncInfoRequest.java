package vn.group24.shopalbackend.controller.request;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class CustomerSyncInfoRequest {
    private Integer customerId;
    private Integer enterpriseId;
    private Integer membershipId;
}
