package vn.group24.shopalbackend.repository;

import java.util.List;

import vn.group24.shopalbackend.controller.request.CatalogSearchCriteriaRequest;
import vn.group24.shopalbackend.domain.Catalog;

/**
 *
 * @author ttg
 */
public interface CatalogRepositoryCustom {

    List<Catalog> getByCriteria(CatalogSearchCriteriaRequest criteria);
}
