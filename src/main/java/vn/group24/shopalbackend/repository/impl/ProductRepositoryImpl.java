package vn.group24.shopalbackend.repository.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import vn.group24.shopalbackend.controller.request.ProductSearchCriteriaRequest;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.domain.QCatalog;
import vn.group24.shopalbackend.domain.QEnterprise;
import vn.group24.shopalbackend.domain.QProduct;
import vn.group24.shopalbackend.domain.QProductCatalog;
import vn.group24.shopalbackend.domain.QProductPoint;
import vn.group24.shopalbackend.repository.ProductRepositoryCustom;
import vn.group24.shopalbackend.security.domain.enums.UserRole;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Product getProductDetailById(Integer productId) {
        QProduct qProduct = QProduct.product;
        QProductCatalog qProductCatalog = QProductCatalog.productCatalog;
        QProductPoint qProductPoint = QProductPoint.productPoint;

        BooleanExpression condition = qProduct.id.eq(productId)
                .and(qProductPoint.active.isTrue());

        return new JPAQuery<Product>(em)
                .from(qProduct)
                .leftJoin(qProduct.productImages).fetchJoin()

                .leftJoin(qProduct.productPoints, qProductPoint).fetchJoin()
                .leftJoin(qProductPoint.enterprise).fetchJoin()

                .leftJoin(qProduct.productCatalogs, qProductCatalog).fetchJoin()
                .leftJoin(qProductCatalog.catalog).fetchJoin()

                .where(condition)
                .fetchFirst();
    }

    @Override
    public List<Product> getByCriteria(ProductSearchCriteriaRequest criteria) {
        QProduct qProduct = QProduct.product;
        QProductCatalog qProductCatalog = QProductCatalog.productCatalog;
        QProductPoint qProductPoint = QProductPoint.productPoint;
        QEnterprise qEnterprise = QEnterprise.enterprise;
        QCatalog qCatalog = QCatalog.catalog;

        BooleanExpression condition = qProduct.id.isNotNull();

        if (UserRole.CUSTOMER == criteria.getUserRole()) {
            condition = condition.and(qProductPoint.active.isTrue());
        }
        
        if (CollectionUtils.isNotEmpty(criteria.getCatalogIdList())) {
            condition = condition.and(qCatalog.id.in(criteria.getCatalogIdList()));
        }

        if (criteria.getRatingMin() != null) {
            condition = condition.and(qProduct.rating.goe(criteria.getRatingMin()));
        }

        if (criteria.getRatingMax() != null) {
            condition = condition.and(qProduct.rating.loe(criteria.getRatingMin()));
        }

        if (StringUtils.isNotBlank(criteria.getKeyword())) {
            condition = condition.and(qProduct.productName.containsIgnoreCase(criteria.getKeyword()));
        }

        JPAQuery<Product> query = new JPAQuery<Product>(em)
                .from(qProduct)
                .leftJoin(qProduct.productImages).fetchJoin()

                .leftJoin(qProduct.productPoints, qProductPoint).fetchJoin()
                .leftJoin(qProductPoint.enterprise, qEnterprise).fetchJoin()

                .leftJoin(qProduct.productCatalogs, qProductCatalog).fetchJoin()
                .leftJoin(qProductCatalog.catalog, qCatalog).fetchJoin();

        query = query.where(condition)
                .limit(criteria.getLimit()).offset(criteria.getOffset())
                .orderBy(qProduct.id.asc());

        return query.fetch();
    }
}
