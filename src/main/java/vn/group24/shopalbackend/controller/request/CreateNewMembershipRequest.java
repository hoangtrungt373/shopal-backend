package vn.group24.shopalbackend.controller.request;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class CreateNewMembershipRequest {
    private String registerEmail;
    private String registerPhoneNumber;
}
