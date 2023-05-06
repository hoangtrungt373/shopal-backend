package vn.group24.shopalbackend.controller.request;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class UpdateProductCartRequest {
    private Integer productCartId;
    private Integer amount;
    private Integer productPointId;
}
