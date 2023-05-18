package vn.group24.shopalbackend.controller.request;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.security.domain.enums.UserRole;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public abstract class AbstractSearchCriteria {
    private Integer limit;
    private Integer offset;
    private UserRole userRole;
}
