package vn.group24.shopalbackend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.group24.shopalbackend.controller.request.CatalogSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.common.CatalogDto;
import vn.group24.shopalbackend.domain.Catalog;
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

    public List<CatalogDto> getAllCatalog() {
        // TODO: recheck this to avoid stackoverflow
        List<Catalog> catalogs = catalogRepository.findAll();
        return catalogMapper.mapToCatalogDtos(catalogs);
    }

    @Override
    public List<CatalogDto> getByCriteria(CatalogSearchCriteriaRequest criteria) {
        List<Catalog> catalogs = catalogRepository.findAll();
        return null;
    }
}
