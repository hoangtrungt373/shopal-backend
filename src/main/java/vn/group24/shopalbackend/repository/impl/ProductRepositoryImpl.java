package vn.group24.shopalbackend.repository.impl;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.domain.QProduct;
import vn.group24.shopalbackend.repository.ProductRepositoryCustom;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Product getProductDetail(Integer productId) {
        QProduct qProduct = QProduct.product;
        return new JPAQuery<Product>(em)
                .from(qProduct)
                .leftJoin(qProduct.productImages).fetchJoin()
                .leftJoin(qProduct.productRelationships).fetchJoin()
                .where(qProduct.id.eq(productId))
                .fetchFirst();
    }
}
