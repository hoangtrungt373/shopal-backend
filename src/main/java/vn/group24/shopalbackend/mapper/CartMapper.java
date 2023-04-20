package vn.group24.shopalbackend.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.common.CartDto;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.controller.response.common.ProductPointDto;
import vn.group24.shopalbackend.controller.response.specific.ProductCartDto;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.ProductCart;
import vn.group24.shopalbackend.domain.ProductImage;
import vn.group24.shopalbackend.domain.ProductPoint;
import vn.group24.shopalbackend.util.LanguageUtils;

@Component
public class CartMapper {

    @Autowired
    private LanguageUtils languageUtils;

    public CartDto mapToCartDto(List<ProductCart> productCarts) {
        CartDto dto = new CartDto();
        dto.setProductCarts(productCarts.stream().map(this::mapToProductCartDto).toList());
        return dto;
    }

    private ProductCartDto mapToProductCartDto(ProductCart productCart) {
        ProductCartDto dto = new ProductCartDto();
        dto.setId(productCart.getId());
        dto.setProductName(productCart.getProduct().getProductName());
        dto.setQuantityInStock(productCart.getProduct().getQuantityInStock());
        dto.setAmountSelected(productCart.getAmount());
        dto.setMainImgUrl(productCart.getProduct().getProductImages().stream()
                .filter(ProductImage::getIsMainImg)
                .findFirst()
                .map(ProductImage::getImageUrl)
                .orElseGet(() -> productCart.getProduct().getProductImages().stream()
                        .findAny()
                        .map(ProductImage::getImageUrl)
                        .orElseGet(() -> null)));
        dto.setExchangeAblePoints(productCart.getProduct().getProductPoints().stream().map(this::mapToProductPointDto).toList());
        dto.setPointSelected(dto.getExchangeAblePoints().stream()
                .filter(point -> point.getId().equals(productCart.getProductPoint().getId()))
                .findFirst()
                .orElseGet(() -> null));
        return dto;
    }


    private ProductPointDto mapToProductPointDto(ProductPoint entity) {
        ProductPointDto dto = new ProductPointDto();
        dto.setId(entity.getId());
        dto.setPointExchange(entity.getPointExchange());
        dto.setPointName(entity.getPointName());
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
