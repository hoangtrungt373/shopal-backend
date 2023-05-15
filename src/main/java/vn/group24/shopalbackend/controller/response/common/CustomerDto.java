package vn.group24.shopalbackend.controller.response.common;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.Gender;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class CustomerDto extends AbstractDto {
    private String contactEmail;
    private String fullName;
    private String nickName;
    private String phoneNumber;
    private Gender gender;
    private String genderDescription;
    private LocalDate birthDate;
    private String avatarUrl;
    private String address;
}
