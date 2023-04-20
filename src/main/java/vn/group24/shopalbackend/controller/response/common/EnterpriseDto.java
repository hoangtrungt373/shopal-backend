package vn.group24.shopalbackend.controller.response.common;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class EnterpriseDto extends AbstractDto {
    private String enterpriseName;
    private String phoneNumber;
    private String address;
    private String websiteUrl;
    private String logoUrl;
}
