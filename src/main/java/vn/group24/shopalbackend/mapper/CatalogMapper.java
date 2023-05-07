package vn.group24.shopalbackend.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.common.CatalogDto;
import vn.group24.shopalbackend.domain.Catalog;
import vn.group24.shopalbackend.domain.multilingual.CatalogStatusLan;
import vn.group24.shopalbackend.util.LanguageUtils;

/**
 *
 * @author ttg
 */
@Component
public class CatalogMapper {

    @Autowired
    private LanguageUtils languageUtils;

    public List<CatalogDto> mapToCatalogDtos(List<Catalog> catalogs) {
        List<CatalogDto> catalogDtos = new ArrayList<>();
        catalogs.forEach(catalog -> {
            CatalogDto catalogDto = mapToCatalogDto(catalog);
            catalogDto.setChildCatalogs(catalog.getChildCatalogs().stream().map(childCatalog -> {
                CatalogDto childCatalogDto = mapToCatalogDto(childCatalog);
                childCatalogDto.setParentCatalogName(catalogDto.getCatalogName());
                return childCatalogDto;
            }).toList());
            Optional.ofNullable(catalog.getParentCatalog())
                    .ifPresent(parentCatalog -> {
                        catalogDto.setParentCatalogName(parentCatalog.getCatalogName());
                        catalogDto.setParentCatalogId(parentCatalog.getId());
                    });
            catalogDtos.add(catalogDto);
        });
        return catalogDtos;
    }

    public CatalogDto mapToCatalogDto(Catalog catalog) {
        CatalogDto catalogDto = new CatalogDto();
        catalogDto.setId(catalog.getId());
        catalogDto.setCatalogName(catalog.getCatalogName());
        catalogDto.setLevel(catalog.getLevel());
        catalogDto.setLogoUrl(catalog.getLogoUrl());
        catalogDto.setTotalSell(catalog.getTotalSell());
        catalogDto.setTotalProduct(catalog.getTotalProduct());
        catalogDto.setProductTrendingState(catalog.getProductTrendingState());
        catalogDto.setCatalogStatus(catalog.getCatalogStatus());
        catalogDto.setCatalogStatusDescription(languageUtils.getEnumDescription(catalog.getCatalogStatus(), CatalogStatusLan.TABLE_NAME));
        return catalogDto;
    }
}
