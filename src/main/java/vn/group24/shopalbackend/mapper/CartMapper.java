package vn.group24.shopalbackend.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.common.CartDto;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.controller.response.common.ProductPointDto;
import vn.group24.shopalbackend.controller.response.customer.ProductCartDto;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.ProductCart;
import vn.group24.shopalbackend.domain.ProductGallery;
import vn.group24.shopalbackend.domain.ProductPoint;

@Component
public class CartMapper {

    public CartDto mapToCartDto(List<ProductCart> productCarts) {
        CartDto dto = new CartDto();
        dto.setProductCarts(productCarts.stream().map(this::mapToProductCartDto).toList());
        return dto;
    }

    private ProductCartDto mapToProductCartDto(ProductCart productCart) {
        ProductCartDto dto = new ProductCartDto();
        dto.setId(productCart.getId());
        dto.setProductId(productCart.getProductPoint().getProduct().getId());
        dto.setProductName(productCart.getProductPoint().getProduct().getProductName());
        dto.setProductStatus(productCart.getProductPoint().getProduct().getProductStatus());
        dto.setProductStatusDescription(productCart.getProductPoint().getProduct().getProductStatus().getTextForCurrentLan());
        dto.setQuantityInStock(productCart.getProductPoint().getProduct().getQuantityInStock());
        dto.setAmountSelected(productCart.getAmount());
        dto.setMainImgUrl(productCart.getProductPoint().getProduct().getProductGalleries().stream()
                .filter(ProductGallery::getIsMainFile)
                .findFirst()
                .map(ProductGallery::getFileUrl)
                .orElseGet(() -> productCart.getProductPoint().getProduct().getProductGalleries().stream()
                        .findAny()
                        .map(ProductGallery::getFileUrl)
                        .orElseGet(() -> null)));
        dto.setExchangeAblePoints(productCart.getProductPoint().getProduct().getProductPoints().stream().map(this::mapToProductPointDto).toList());
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
