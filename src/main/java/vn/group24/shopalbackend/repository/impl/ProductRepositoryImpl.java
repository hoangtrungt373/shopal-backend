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
import vn.group24.shopalbackend.domain.enums.ProductStatus;
import vn.group24.shopalbackend.repository.ProductRepositoryCustom;
import vn.group24.shopalbackend.security.domain.enums.UserRole;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    QProduct qProduct = QProduct.product;
    QProductCatalog qProductCatalog = QProductCatalog.productCatalog;
    QProductPoint qProductPoint = QProductPoint.productPoint;
    QEnterprise qEnterprise = QEnterprise.enterprise;
    QCatalog qCatalog = new QCatalog("qCatalog");
    QCatalog qParentCatalog = new QCatalog("qParentCatalog");

    @Override
    public Product getDetailById(Integer productId) {
        BooleanExpression condition = qProduct.id.eq(productId);

        return new JPAQuery<Product>(em)
                .from(qProduct)
                .leftJoin(qProduct.productGalleries).fetchJoin()

                .leftJoin(qProduct.productPoints, qProductPoint).fetchJoin()
                .leftJoin(qProductPoint.enterprise).fetchJoin()

                .leftJoin(qProduct.productCatalogs, qProductCatalog).fetchJoin()
                .leftJoin(qProductCatalog.catalog).fetchJoin()

                .where(condition)
                .fetchFirst();
    }

    @Override
    public List<Product> getByCriteria(ProductSearchCriteriaRequest criteria) {
        BooleanExpression condition = buildSearchCriteria(criteria);

        JPAQuery<Product> query = new JPAQuery<Product>(em)
                .from(qProduct)
                .leftJoin(qProduct.productGalleries).fetchJoin()

                .leftJoin(qProduct.productPoints, qProductPoint).fetchJoin()
                .leftJoin(qProductPoint.enterprise, qEnterprise).fetchJoin()

                .leftJoin(qProduct.productCatalogs, qProductCatalog).fetchJoin()
                .leftJoin(qProductCatalog.catalog, qCatalog).fetchJoin()
                .leftJoin(qCatalog.parentCatalog, qParentCatalog).fetchJoin();

        query = query.where(condition)
                .limit(criteria.getLimit()).offset(criteria.getOffset())
                .orderBy(qProduct.id.asc());

        return query.fetch();
    }

    @Override
    public Integer countByCriteria(ProductSearchCriteriaRequest criteria) {
        BooleanExpression condition = buildSearchCriteria(criteria);

        JPAQuery<Integer> query = new JPAQuery<Product>(em)
                .select(qProduct.id)
                .from(qProduct)
                .leftJoin(qProduct.productGalleries)

                .leftJoin(qProduct.productPoints, qProductPoint)
                .leftJoin(qProductPoint.enterprise, qEnterprise)

                .leftJoin(qProduct.productCatalogs, qProductCatalog)
                .leftJoin(qProductCatalog.catalog, qCatalog)
                .leftJoin(qCatalog.parentCatalog, qParentCatalog);

        query = query.where(condition);

        return query.fetch().size();
    }

    private BooleanExpression buildSearchCriteria(ProductSearchCriteriaRequest criteria) {
        BooleanExpression condition = qProduct.id.isNotNull();

        if (criteria.getUserRole() == UserRole.CUSTOMER) {
            condition = condition.and(qProductPoint.active.isTrue());
            condition = condition.and(qProduct.productStatus.eq(ProductStatus.ACTIVE));
        }

        if (criteria.getProductId() != null) {
            condition = condition.and(qProduct.id.eq(criteria.getProductId()));
        }

        if (CollectionUtils.isNotEmpty(criteria.getCatalogIdList())) {
            condition = condition.and(qCatalog.id.in(criteria.getCatalogIdList()).or(
                    qParentCatalog.id.in(criteria.getCatalogIdList())
            ));
        }

        if (criteria.getRatingMin() != null) {
            condition = condition.and(qProduct.rating.goe(criteria.getRatingMin()));
        }

        if (criteria.getRatingMax() != null) {
            condition = condition.and(qProduct.rating.loe(criteria.getRatingMin()));
        }

        if (StringUtils.isNotBlank(criteria.getKeyword())) {
            condition = condition.and(qProduct.productName.containsIgnoreCase(criteria.getKeyword())
                    .or(qCatalog.catalogName.containsIgnoreCase(criteria.getKeyword())
                            .or(qParentCatalog.catalogName.containsIgnoreCase(criteria.getKeyword()))));
        }

        if (StringUtils.isNotBlank(criteria.getSku())) {
            condition = condition.and(qProduct.sku.eq(criteria.getSku()));
        }

        if (criteria.getProductStatus() != null) {
            condition = condition.and(qProduct.productStatus.eq(criteria.getProductStatus()));
        }

        if (criteria.getProductType() != null) {
            condition = condition.and(qProduct.productType.eq(criteria.getProductType()));
        }

        if (criteria.getInputDateFrom() != null) {
            condition = condition.and(qProduct.inputDate.goe(criteria.getInputDateFrom()));
        }

        if (criteria.getInputDateTo() != null) {
            condition = condition.and(qProduct.inputDate.loe(criteria.getInputDateTo()));
        }

        if (criteria.getExpirationDateFrom() != null) {
            condition = condition.and(qProduct.expirationDate.goe(criteria.getExpirationDateFrom()));
        }

        if (criteria.getExpirationDateTo() != null) {
            condition = condition.and(qProduct.expirationDate.loe(criteria.getExpirationDateTo()));
        }

        if (criteria.getInitialCashFrom() != null) {
            condition = condition.and(qProduct.initialCash.goe(criteria.getInitialCashFrom()));
        }

        if (criteria.getInitialCashTo() != null) {
            condition = condition.and(qProduct.initialCash.loe(criteria.getInitialCashTo()));
        }

        return condition;
    }
}
