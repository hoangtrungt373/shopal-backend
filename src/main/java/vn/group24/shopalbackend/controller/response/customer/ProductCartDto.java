package vn.group24.shopalbackend.controller.response.customer;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.common.AbstractDto;
import vn.group24.shopalbackend.controller.response.common.ProductPointDto;
import vn.group24.shopalbackend.domain.enums.ProductStatus;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class ProductCartDto extends AbstractDto {
    private String productName;
    private ProductStatus productStatus;
    private String productStatusDescription;
    private Integer productId;
    private Integer quantityInStock;
    private Boolean active;
    private String mainImgUrl;
    // TODO: add this column
    private String imgText;
    private Integer amountSelected;
    private ProductPointDto pointSelected;
    private List<ProductPointDto> exchangeAblePoints = new ArrayList<>();
}
