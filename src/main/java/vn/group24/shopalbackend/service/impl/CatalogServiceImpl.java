package vn.group24.shopalbackend.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.group24.shopalbackend.controller.request.CatalogSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.common.CatalogDto;
import vn.group24.shopalbackend.domain.Catalog;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.domain.ProductCatalog;
import vn.group24.shopalbackend.domain.ProductPoint;
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
                    .map(ProductCatalog::getProduct).distinct().map(Product::getTotalSold).reduce(0, Integer::sum);
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
            Integer totalSell = catalog.getProductCatalogs().stream().map(ProductCatalog::getProduct).distinct().map(Product::getTotalSold).reduce(0, Integer::sum);
            catalog.setTotalSell(totalSell);
            catalog.setTotalProduct(totalProduct);
            catalog.setProductTrendingState(Arrays.stream(ProductTrendingState.values()).findAny().orElseGet(() -> ProductTrendingState.MEDIUM));
        });
        return catalogMapper.mapToCatalogDtos(catalogsLv1);
    }

    @Override
    public List<CatalogDto> getByCriteria(CatalogSearchCriteriaRequest criteria) {
        List<Catalog> catalogs = catalogRepository.findAll().stream().filter(catalog -> catalogPredicate(criteria).test(catalog)).toList();

        if (CollectionUtils.isEmpty(catalogs)) {
            catalogs = catalogRepository.findAll().stream().filter(catalog -> catalog.getLevel().equals(1)).toList();
        }

        return catalogMapper.mapToCatalogDtos(catalogs);
    }

    private Predicate<Catalog> catalogPredicate(CatalogSearchCriteriaRequest criteria) {
        Predicate<Catalog> predicate = catalog -> catalog.getId() != null;

        if (CollectionUtils.isNotEmpty(criteria.getEnterpriseIds())) {
            predicate = predicate.and(catalog -> catalog.getProductCatalogs().stream().flatMap(pc -> pc.getProduct().getProductPoints().stream())
                    .map(ProductPoint::getEnterprise).anyMatch(enterprise -> criteria.getEnterpriseIds().contains(enterprise.getId())));
        }

        if (criteria.getParentCatalogId() != null) {
            predicate = predicate.and(catalog -> catalog.getParentCatalog() != null).and(catalog ->
                    Optional.ofNullable(catalog.getParentCatalog())
                            .map(Catalog::getId)
                            .orElseGet(() -> 0).equals(criteria.getParentCatalogId()));
        }

        if (criteria.getChildCatalogId() != null) {
            Catalog childCatalog = catalogRepository.findById(criteria.getChildCatalogId()).orElseThrow(() ->
                    new IllegalArgumentException(String.format("Can not found Catalog with id = %s", criteria.getChildCatalogId())));

            predicate = predicate.and(catalog -> catalog.getParentCatalog() != null && catalog.getParentCatalog().equals(childCatalog.getParentCatalog()));
        }

        if (criteria.getProductKeyword() != null) {
            predicate = predicate.and(catalog -> catalog.getProductCatalogs().stream().map(ProductCatalog::getProduct).anyMatch(
                    product -> StringUtils.containsIgnoreCase(product.getProductName(), criteria.getProductKeyword())
            )).or(catalog -> StringUtils.containsIgnoreCase(catalog.getCatalogName(), criteria.getProductKeyword()));
        }

        return predicate;
    }
}
