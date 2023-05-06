package vn.group24.shopalbackend.controller.response.common;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.ProductSource;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class ProductPointDto extends AbstractDto {
    private EnterpriseDto enterprise;
    private ProductSource source;
    private String sourceDescription;
    private BigDecimal pointExchange;
    private String updateDescription;
    private Boolean active;
    private String pointName;
}
