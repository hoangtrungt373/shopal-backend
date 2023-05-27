package vn.group24.shopalbackend.domain.dto;

import java.time.LocalDateTime;

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
    private Integer productId;
    private ProductDto product;
    private Integer enterpriseId;
    private EnterpriseDto enterprise;
    private Integer announcementId;
    private LocalDateTime date;
}
