package vn.group24.shopalbackend.mapper;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.ProductReviewDto;
import vn.group24.shopalbackend.controller.response.common.CatalogDto;
import vn.group24.shopalbackend.controller.response.common.CustomerDto;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.controller.response.common.ProductImageDto;
import vn.group24.shopalbackend.controller.response.common.ProductPointDto;
import vn.group24.shopalbackend.controller.response.customer.ProductDetailDto;
import vn.group24.shopalbackend.domain.Catalog;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.domain.ProductCatalog;
import vn.group24.shopalbackend.domain.ProductImage;
import vn.group24.shopalbackend.domain.ProductPoint;
import vn.group24.shopalbackend.domain.ProductReview;

@Component
public class ProductDetailMapper {

    public ProductDetailDto mapToProductDetailDto(Product product) {
        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto.setId(product.getId());
        productDetailDto.setProductName(product.getProductName());
        productDetailDto.setSku(product.getSku());
        productDetailDto.setProductDescriptionUrl(product.getProductDescriptionUrl());
        productDetailDto.setQuantityInStock(product.getQuantityInStock());
        productDetailDto.setRating(product.getRating());
        productDetailDto.setProductStatus(product.getProductStatus());
        productDetailDto.setProductStatusDescription(product.getProductStatus().getTextForCurrentLan());
        productDetailDto.setImageUrls(product.getProductImages().stream().map(this::mapToProductImageDto).toList());
        productDetailDto.setCatalogs(product.getProductCatalogs().stream().map(ProductCatalog::getCatalog).map(this::mapToCatalogDto).toList());
        productDetailDto.setExchangeAblePoints(product.getProductPoints().stream().map(this::mapToProductPointDto).toList());
        productDetailDto.setTotalSold(product.getTotalSold());
        productDetailDto.setInputDate(product.getInputDate());
        productDetailDto.setInitialCash(product.getInitialCash());
        productDetailDto.setExpirationDate(product.getExpirationDate());
        productDetailDto.setProductType(product.getProductType());
        productDetailDto.setTotalReview(product.getTotalReview());
        productDetailDto.setReviews(product.getProductReviews().stream().map(this::mapToProductReviewDto).toList());
        return productDetailDto;
    }

    public ProductImageDto mapToProductImageDto(ProductImage entity) {
        ProductImageDto dto = new ProductImageDto();
        dto.setId(entity.getId());
        dto.setImageUrl(entity.getImageUrl());
        dto.setIsMainImg(entity.getIsMainImg());
        return dto;
    }

    public CatalogDto mapToCatalogDto(Catalog entity) {
        CatalogDto catalogDto = new CatalogDto();
        catalogDto.setId(entity.getId());
        catalogDto.setCatalogName(entity.getCatalogName());
        catalogDto.setLevel(entity.getLevel());
        catalogDto.setLogoUrl(entity.getLogoUrl());
        return catalogDto;
    }

    private ProductPointDto mapToProductPointDto(ProductPoint entity) {
        ProductPointDto dto = new ProductPointDto();
        dto.setId(entity.getId());
        dto.setPointExchange(entity.getPointExchange());
        dto.setActive(entity.getActive());
        dto.setEnterprise(mapToEnterpriseDto(entity.getEnterprise()));
        return dto;
    }

    private EnterpriseDto mapToEnterpriseDto(Enterprise entity) {
        EnterpriseDto dto = new EnterpriseDto();
        dto.setId(entity.getId());
        dto.setEnterpriseName(entity.getEnterpriseName());
        dto.setLogoUrl(entity.getLogoUrl());
        return dto;
    }

    private ProductReviewDto mapToProductReviewDto(ProductReview productReview) {
        ProductReviewDto productReviewDto = new ProductReviewDto();
        productReviewDto.setId(productReview.getId());
        productReviewDto.setReviewType(productReview.getReviewType());
        productReviewDto.setAmountLike(productReview.getAmountLike());
        productReviewDto.setReviewDate(productReview.getReviewDate());
        productReviewDto.setContent(productReview.getContent());
        productReviewDto.setRating(productReview.getRating());
        String imageUrlStr = productReview.getImageUrls().replace("[", "").replace("]", "");
        if (StringUtils.isNotBlank(imageUrlStr)) {
            List<String> imageUrls = Arrays.stream(imageUrlStr.split(",", 5)).map(StringUtils::trim).toList();
            productReviewDto.setImageUrls(imageUrls);
        }
        productReviewDto.setCustomer(mapToCustomerDto(productReview.getCustomer()));
        return productReviewDto;
    }

    private CustomerDto mapToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setAvatarUrl(customer.getAvatarUrl());
        customerDto.setFullName(customer.getFullName());
        return customerDto;
    }
}
