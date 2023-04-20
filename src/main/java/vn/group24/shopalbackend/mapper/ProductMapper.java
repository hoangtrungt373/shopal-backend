package vn.group24.shopalbackend.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.common.CatalogDto;
import vn.group24.shopalbackend.controller.response.common.ProductImageDto;
import vn.group24.shopalbackend.controller.response.specific.ProductDetailDto;
import vn.group24.shopalbackend.domain.Catalog;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.domain.ProductCatalog;
import vn.group24.shopalbackend.domain.ProductImage;
import vn.group24.shopalbackend.domain.multilingual.ProductTypeLan;
import vn.group24.shopalbackend.util.LanguageUtils;

@Component
public class ProductMapper {

    @Autowired
    private LanguageUtils languageUtils;

    public ProductDetailDto mapToProductDetailDto(Product entity) {
        ProductDetailDto dto = new ProductDetailDto();
        dto.setId(entity.getId());
        dto.setProductName(entity.getProductName());
        dto.setActive(entity.getActive());
        dto.setSku(entity.getSku());
        dto.setDescriptionContentUrl(entity.getDescriptionContentUrl());
        dto.setQuantityInStock(entity.getQuantityInStock());
        dto.setImageUrls(entity.getProductImages().stream().map(this::mapToProductImageDto).toList());
        dto.setCatalogs(entity.getProductCatalogs().stream().map(ProductCatalog::getCatalog).map(this::mapToCatalogDto).toList());
        return dto;
    }

    public ProductImageDto mapToProductImageDto(ProductImage entity) {
        ProductImageDto dto = new ProductImageDto();
        dto.setId(entity.getId());
        dto.setImageUrl(entity.getImageUrl());
        dto.setIsMainImg(entity.getIsMainImg());
        return dto;
    }

    public CatalogDto mapToCatalogDto(Catalog entity) {
        CatalogDto dto = new CatalogDto();
        dto.setId(entity.getId());
        dto.setProductType(entity.getProductType());
        dto.setProductTypeDescription(languageUtils.getEnumDescription(entity.productType, ProductTypeLan.TABLE_NAME));
        dto.setLevel(entity.getLevel());
        dto.setLogoUrl(entity.getLogoUrl());
        return dto;
    }
}
