package vn.group24.shopalbackend.controller.request;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.Gender;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class CustomerSearchCriteriaRequest extends AbstractSearchCriteria {
    private Integer customerId;
    private LocalDate joinDateFrom;
    private LocalDate joinDateTo;
    private String customerName;
    private String customerEmail;
    private String customerPhoneNumber;
    private Gender gender;
    private List<Integer> associateEnterpriseIds;
    private String address;
}
