package vn.group24.shopalbackend.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.group24.shopalbackend.controller.request.CatalogSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.common.CatalogDto;
import vn.group24.shopalbackend.domain.Catalog;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.domain.ProductCatalog;
import vn.group24.shopalbackend.domain.enums.ProductTrendingState;
import vn.group24.shopalbackend.mapper.CatalogMapper;
import vn.group24.shopalbackend.repository.CatalogRepository;
import vn.group24.shopalbackend.service.CatalogService;

/**
 * @author ttg
 */
@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private CatalogMapper catalogMapper;

    @Override
    public List<CatalogDto> getAllMainCatalog() {
        // TODO: handle for other lv above 2
        List<Catalog> catalogsLv1 = catalogRepository.getByLevel(1);
        return catalogMapper.mapToCatalogDtos(catalogsLv1);
    }

    @Override
    public List<CatalogDto> getAllChildCatalog() {
        // TODO: handle for other lv above 2
        List<Catalog> catalogsLv1 = catalogRepository.getByLevel(2);
        return catalogMapper.mapToCatalogDtos(catalogsLv1);
    }

    @Override
    public List<CatalogDto> getAllMainCatalogWithDetail() {
        // TODO: handle for other lv above 2
        List<Catalog> catalogsLv1 = catalogRepository.getByLevel(1);
        catalogsLv1.forEach(catalog -> {
            Integer totalProduct = catalog.getChildCatalogs().stream().map(childCatalog -> childCatalog.getProductCatalogs().size()).reduce(0, Integer::sum);
            Integer totalSell = catalog.getChildCatalogs().stream().flatMap(childCatalog -> childCatalog.getProductCatalogs().stream())
                    .map(ProductCatalog::getProduct).distinct().map(Product::getAmountSold).reduce(0, Integer::sum);
            catalog.setTotalSell(totalSell);
            catalog.setTotalProduct(totalProduct);
            catalog.setProductTrendingState(Arrays.stream(ProductTrendingState.values()).findAny().orElseGet(() -> ProductTrendingState.MEDIUM));
        });
        return catalogMapper.mapToCatalogDtos(catalogsLv1);
    }

    @Override
    public List<CatalogDto> getAllChildCatalogWithDetail() {
        // TODO: handle for other lv above 2
        List<Catalog> catalogsLv1 = catalogRepository.getByLevel(2);
        catalogsLv1.forEach(catalog -> {
            Integer totalProduct = catalog.getProductCatalogs().size();
            Integer totalSell = catalog.getProductCatalogs().stream().map(ProductCatalog::getProduct).distinct().map(Product::getAmountSold).reduce(0, Integer::sum);
            catalog.setTotalSell(totalSell);
            catalog.setTotalProduct(totalProduct);
            catalog.setProductTrendingState(Arrays.stream(ProductTrendingState.values()).findAny().orElseGet(() -> ProductTrendingState.MEDIUM));
        });
        return catalogMapper.mapToCatalogDtos(catalogsLv1);
    }

    @Override
    public List<CatalogDto> getByCriteria(CatalogSearchCriteriaRequest criteria) {
        List<Catalog> catalogs = catalogRepository.findAll();
        return null;
    }
}
