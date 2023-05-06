package vn.group24.shopalbackend.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.common.CatalogDto;
import vn.group24.shopalbackend.domain.Catalog;
import vn.group24.shopalbackend.domain.multilingual.ProductTypeLan;
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
            CatalogDto catalogDto = new CatalogDto();
            catalogDto.setId(catalog.getId());
            catalogDto.setProductType(catalog.getProductType());
            catalogDto.setLevel(catalog.getLevel());
            catalogDto.setProductTypeDescription(languageUtils.getEnumDescription(catalog.getProductType(), ProductTypeLan.TABLE_NAME));
            catalogDto.setLogoUrl(catalog.getLogoUrl());
            catalogDto.setChildCatalogs(mapToCatalogDtos(new ArrayList<>(catalog.getChildCatalogs())));
            catalogDtos.add(catalogDto);
        });
        return catalogDtos;
    }
}
