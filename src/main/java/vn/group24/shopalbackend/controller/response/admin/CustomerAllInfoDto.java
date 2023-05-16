package vn.group24.shopalbackend.controller.response.admin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
public class CustomerAllInfoDto extends AbstractDto {
    private String contactEmail;
    private String fullName;
    private String nickName;
    private String phoneNumber;
    private Gender gender;
    private String genderDescription;
    private LocalDate birthDate;
    private String avatarUrl;
    private String address;
    private LocalDate joinDate;
    private Integer totalBuy;
    private List<MembershipPointDto> membershipPoints = new ArrayList<>();

}

