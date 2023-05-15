package vn.group24.shopalbackend.controller.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.controller.response.common.AbstractDto;
import vn.group24.shopalbackend.controller.response.common.CustomerDto;
import vn.group24.shopalbackend.controller.response.common.ProductDto;
import vn.group24.shopalbackend.domain.enums.ProductReviewType;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class ProductReviewDto extends AbstractDto {
    private ProductDto product;
    private CustomerDto customer;
    private BigDecimal rating;
    private String content;
    private List<String> imageUrls = new ArrayList<>();
    private Integer amountLike;
    private ProductReviewDto parentReview;
    private ProductReviewType reviewType;
    private LocalDateTime reviewDate;
    private Set<ProductReviewDto> childReviews = new HashSet<>();
}
