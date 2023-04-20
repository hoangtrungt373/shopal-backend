package vn.group24.shopalbackend.controller.response.common;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.specific.ProductCartDto;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class CartDto {
    private List<ProductCartDto> productCarts = new ArrayList<>();
}
