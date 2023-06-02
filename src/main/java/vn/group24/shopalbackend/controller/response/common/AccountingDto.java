package vn.group24.shopalbackend.controller.response.common;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.PaymentMethod;
import vn.group24.shopalbackend.domain.enums.PaymentStatus;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class AccountingDto extends AbstractDto {
    private EnterpriseDto enterprise;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalPoint;
    private BigDecimal totalIncome;
    private BigDecimal totalCommission;
    private PaymentStatus paymentStatus;
    private String paymentStatusDescription;
    private LocalDateTime paymentDate;
    private PaymentMethod paymentMethod;
    private String paymentMethodDescription;
    private BigDecimal commissionRate;
}
