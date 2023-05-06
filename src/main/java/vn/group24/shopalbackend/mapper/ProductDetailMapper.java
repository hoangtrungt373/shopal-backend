package vn.group24.shopalbackend.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.common.CatalogDto;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.controller.response.common.ProductImageDto;
import vn.group24.shopalbackend.controller.response.common.ProductPointDto;
import vn.group24.shopalbackend.controller.response.customer.ProductDetailDto;
import vn.group24.shopalbackend.domain.Catalog;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.domain.ProductCatalog;
import vn.group24.shopalbackend.domain.ProductImage;
import vn.group24.shopalbackend.domain.ProductPoint;
import vn.group24.shopalbackend.domain.multilingual.ProductStatusLan;
import vn.group24.shopalbackend.domain.multilingual.ProductTypeLan;
import vn.group24.shopalbackend.util.LanguageUtils;

@Component
public class ProductDetailMapper {

    @Autowired
    private LanguageUtils languageUtils;

    public ProductDetailDto mapToProductDetailDto(Product product) {
        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto.setId(product.getId());
        productDetailDto.setProductName(product.getProductName());
        productDetailDto.setSku(product.getSku());
        productDetailDto.setDescriptionContentUrl(product.getDescriptionContentUrl());
        productDetailDto.setQuantityInStock(product.getQuantityInStock());
        productDetailDto.setRating(product.getRating());
        productDetailDto.setProductStatus(product.getProductStatus());
        productDetailDto.setProductStatusDescription(languageUtils.getEnumDescription(product.getProductStatus(), ProductStatusLan.TABLE_NAME));
        productDetailDto.setImageUrls(product.getProductImages().stream().map(this::mapToProductImageDto).toList());
        productDetailDto.setCatalogs(product.getProductCatalogs().stream().map(ProductCatalog::getCatalog).map(this::mapToCatalogDto).toList());
        productDetailDto.setExchangeAblePoints(product.getProductPoints().stream().map(this::mapToProductPointDto).toList());
        productDetailDto.setAmountSold(product.getAmountSold());
        productDetailDto.setInputDate(product.getInputDate());
        productDetailDto.setInitialCash(product.getInitialCash());
        productDetailDto.setExpirationDate(product.getExpirationDate());
        productDetailDto.setProductStatus(product.getProductStatus());
        productDetailDto.setProductStatusDescription(languageUtils.getEnumDescription(product.getProductStatus(), ProductStatusLan.TABLE_NAME));
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
        CatalogDto dto = new CatalogDto();
        dto.setId(entity.getId());
        dto.setProductType(entity.getProductType());
        dto.setProductTypeDescription(languageUtils.getEnumDescription(entity.productType, ProductTypeLan.TABLE_NAME));
        dto.setLevel(entity.getLevel());
        dto.setLogoUrl(entity.getLogoUrl());
        return dto;
    }

    private ProductPointDto mapToProductPointDto(ProductPoint entity) {
        ProductPointDto dto = new ProductPointDto();
        dto.setId(entity.getId());
        dto.setPointExchange(entity.getPointExchange());
        dto.setPointName(entity.getPointName());
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
