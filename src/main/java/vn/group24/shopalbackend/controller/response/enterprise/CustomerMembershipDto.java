package vn.group24.shopalbackend.controller.response.enterprise;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.common.AbstractDto;
import vn.group24.shopalbackend.domain.enums.Gender;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class CustomerMembershipDto extends AbstractDto {
    private Integer customerId;
    private String registerEmail;
    private String registerPhoneNumber;
    private String fullName;
    private Gender gender;
    private String genderDescription;
    private LocalDate birthDate;
    private String avatarUrl;
    private String address;
    private BigDecimal availablePoint;
}
