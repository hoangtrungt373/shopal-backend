package vn.group24.shopalbackend.repository.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import vn.group24.shopalbackend.controller.request.CatalogSearchCriteriaRequest;
import vn.group24.shopalbackend.domain.Catalog;
import vn.group24.shopalbackend.domain.QCatalog;
import vn.group24.shopalbackend.domain.QEnterprise;
import vn.group24.shopalbackend.domain.QProduct;
import vn.group24.shopalbackend.domain.QProductCatalog;
import vn.group24.shopalbackend.domain.QProductPoint;
import vn.group24.shopalbackend.repository.CatalogRepositoryCustom;

/**
 *
 * @author ttg
 */
public class CatalogRepositoryImpl implements CatalogRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Catalog> getByCriteria(CatalogSearchCriteriaRequest criteria) {
        QCatalog qCatalog = QCatalog.catalog;
        QProductCatalog qProductCatalog = QProductCatalog.productCatalog;
        QProduct qProduct = QProduct.product;
        QProductPoint qProductPoint = QProductPoint.productPoint;
        QEnterprise qEnterprise = QEnterprise.enterprise;

        return null;
    }
}
