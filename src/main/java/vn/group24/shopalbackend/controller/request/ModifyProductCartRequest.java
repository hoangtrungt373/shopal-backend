package vn.group24.shopalbackend.controller.request;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class ModifyProductCartRequest {
    private Integer productId;
    private Integer amount;
    private Integer productPointId;
}
