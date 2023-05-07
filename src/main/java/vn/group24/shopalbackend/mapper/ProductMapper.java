package vn.group24.shopalbackend.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.controller.response.common.ProductDto;
import vn.group24.shopalbackend.controller.response.common.ProductPointDto;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.domain.ProductImage;
import vn.group24.shopalbackend.domain.ProductPoint;
import vn.group24.shopalbackend.domain.multilingual.ProductStatusLan;
import vn.group24.shopalbackend.util.LanguageUtils;

/**
 *
 * @author ttg
 */
@Component
public class ProductMapper {

    @Autowired
    private LanguageUtils languageUtils;

    public List<ProductDto> mapToProductDtos(List<Product> products) {
        return products.stream().map(product -> {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setProductName(product.getProductName());
            productDto.setSku(product.getSku());
            productDto.setQuantityInStock(product.getQuantityInStock());
            productDto.setRating(product.getRating());
            productDto.setProductStatus(product.getProductStatus());
            productDto.setProductStatusDescription(languageUtils.getEnumDescription(product.getProductStatus(), ProductStatusLan.TABLE_NAME));
            productDto.setAmountSold(product.getAmountSold());
            productDto.setMainImgUrl(product.getProductImages().stream()
                    .filter(ProductImage::getIsMainImg)
                    .findFirst()
                    .map(ProductImage::getImageUrl)
                    .orElseGet(() -> product.getProductImages().stream()
                            .findAny()
                            .map(ProductImage::getImageUrl)
                            .orElseGet(() -> null)));
            productDto.setExchangeAblePoints(product.getProductPoints().stream()
                    .map(this::mapToProductPointDto).toList());
            productDto.setInputDate(product.getInputDate());
            productDto.setExpirationDate(product.getExpirationDate());
            productDto.setInitialCash(product.getInitialCash());
            productDto.setProductType(product.getProductType());
            return productDto;
        }).collect(Collectors.toList());
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
}
