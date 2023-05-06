package vn.group24.shopalbackend.service;

import java.util.List;

import vn.group24.shopalbackend.controller.request.CatalogSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.common.CatalogDto;

/**
 * @author ttg
 */
public interface CatalogService {

    List<CatalogDto> getAllCatalog();

    List<CatalogDto> getByCriteria(CatalogSearchCriteriaRequest criteria);
}
