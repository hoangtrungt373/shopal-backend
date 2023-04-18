package vn.group24.shopalbackend.mapper;

import org.springframework.security.core.parameters.P;
import vn.group24.shopalbackend.controller.response.ProductDetailDto;
import vn.group24.shopalbackend.controller.response.ProductImageDto;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.domain.ProductImage;

import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDetailDto mapToProductDetailDto(Product entity) {
        ProductDetailDto dto = new ProductDetailDto();
        dto.setProductName(entity.getProductName());
        dto.setPrice(entity.getPrice());
        dto.setActive(entity.getActive());
        dto.setSku(entity.getSku());
        dto.setDescriptionContentUrl(entity.getDescriptionContentUrl());
        dto.setQuantityInStock(entity.getQuantityInStock());
        dto.setProductImageDtos(entity.getProductImages().stream().map(ProductMapper::mapToProductImageDto).collect(Collectors.toList()));
        return dto;
    }

    public static ProductImageDto mapToProductImageDto(ProductImage entity) {
        ProductImageDto dto = new ProductImageDto();
        dto.setImageUrl(entity.getImageUrl());
        dto.setIsMainImg(entity.getIsMainImg());
        return dto;
    }
}
