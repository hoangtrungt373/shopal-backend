package vn.group24.shopalbackend.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.domain.QProduct;
import vn.group24.shopalbackend.domain.QProductCatalog;
import vn.group24.shopalbackend.domain.QProductPoint;
import vn.group24.shopalbackend.repository.ProductRepositoryCustom;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Product getProductDetail(Integer productId) {
        QProduct qProduct = QProduct.product;
        QProductCatalog qProductCatalog = QProductCatalog.productCatalog;
        QProductPoint qProductPoint = QProductPoint.productPoint;

        BooleanExpression condition = qProduct.id.eq(productId);

        return new JPAQuery<Product>(em)
                .from(qProduct)
                .leftJoin(qProduct.productImages).fetchJoin()

//                .leftJoin(qProduct.productPoints, qProductPoint).fetchJoin()
//                .leftJoin(qProductPoint.enterprise).fetchJoin()

                .leftJoin(qProduct.productCatalogs, qProductCatalog).fetchJoin()
                .leftJoin(qProductCatalog.catalog).fetchJoin()

                .where(condition)
                .fetchFirst();
    }
}
