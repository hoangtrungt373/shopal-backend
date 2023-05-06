package vn.group24.shopalbackend.controller.response.enterprise;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.common.AbstractDto;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class CustomerRegisterDto extends AbstractDto {
    private Integer stagCustomerId;
    private String registerEmail;
    private String fullName;
    private String registerPhoneNumber;
    private LocalDateTime importDate;
    private BigDecimal initialPoint;
}
