package vn.group24.shopalbackend.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.common.CatalogDto;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.controller.response.common.ProductDto;
import vn.group24.shopalbackend.controller.response.common.ProductPointDto;
import vn.group24.shopalbackend.domain.Catalog;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.domain.ProductCatalog;
import vn.group24.shopalbackend.domain.ProductGallery;
import vn.group24.shopalbackend.domain.ProductPoint;

/**
 *
 * @author ttg
 */
@Component
public class ProductMapper {

    public List<ProductDto> mapToProductDtos(List<Product> products) {
        return products.stream().map(product -> {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setProductName(product.getProductName());
            productDto.setSku(product.getSku());
            productDto.setQuantityInStock(product.getQuantityInStock());
            productDto.setRating(product.getRating());
            productDto.setProductStatus(product.getProductStatus());
            productDto.setProductStatusDescription(product.getProductStatus().getTextForCurrentLan());
            productDto.setTotalSold(product.getTotalSold());
            productDto.setMainImgUrl(product.getProductGalleries().stream()
                    .filter(ProductGallery::getIsMainFile)
                    .findFirst()
                    .map(ProductGallery::getFileUrl)
                    .orElseGet(() -> product.getProductGalleries().stream()
                            .findAny()
                            .map(ProductGallery::getFileUrl)
                            .orElseGet(() -> null)));
            productDto.setExchangeAblePoints(product.getProductPoints().stream().filter(pp -> BooleanUtils.isTrue(pp.getActive()))
                    .map(this::mapToProductPointDto).toList());
            productDto.setInputDate(product.getInputDate());
            productDto.setExpirationDate(product.getExpirationDate());
            productDto.setInitialCash(product.getInitialCash());
            productDto.setProductType(product.getProductType());
            productDto.setProductTypeDescription(product.getProductType().getTextForCurrentLan());
            productDto.setCatalogs(product.getProductCatalogs().stream().map(ProductCatalog::getCatalog).map(this::mapToCatalogDto).collect(Collectors.toList()));
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

    private CatalogDto mapToCatalogDto(Catalog catalog) {
        CatalogDto catalogDto = new CatalogDto();
        catalogDto.setId(catalog.getId());
        catalogDto.setCatalogName(catalog.getCatalogName());
        catalogDto.setLogoUrl(catalog.getLogoUrl());
        return catalogDto;
    }
}
