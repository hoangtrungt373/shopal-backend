package vn.group24.shopalbackend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.group24.shopalbackend.domain.Catalog;
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

    public List<Catalog> getAllCatalog() {
        return catalogRepository.findAll();
    }
}
