package vn.group24.shopalbackend.controller.response.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ttg
 */
@Getter
@Setter
@Builder
public class CreateOrUpdateProductResponse {
    private String message;
    private Integer productId;
    private String sku;
}
