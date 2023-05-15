package vn.group24.shopalbackend.controller.request;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class CustomerNewMembershipRequest {
    private String registerEmail;
    private String registerPhoneNumber;
    private Integer enterpriseId;
}
