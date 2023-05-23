package vn.group24.shopalbackend.domain.dto;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.controller.response.common.ProductDto;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class AbstractAnn {
    private Integer id;
    private ProductDto product;
    private EnterpriseDto enterprise;
}
