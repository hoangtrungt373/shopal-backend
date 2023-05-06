package vn.group24.shopalbackend.repository.impl;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import vn.group24.shopalbackend.domain.ProductCart;
import vn.group24.shopalbackend.domain.QProduct;
import vn.group24.shopalbackend.domain.QProductCart;
import vn.group24.shopalbackend.domain.QProductPoint;
import vn.group24.shopalbackend.repository.ProductCartRepositoryCustom;

public class ProductCartRepositoryImpl implements ProductCartRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ProductCart> getAllProductCartByCustomerId(Integer customerId) {
        QProductCart qProductCart = QProductCart.productCart;
        QProduct qProduct = QProduct.product;
        QProductPoint qProductPoint = QProductPoint.productPoint;

        BooleanExpression condition = qProductCart.customer.id.eq(customerId)
                .and(qProductPoint.active.isTrue());

        // TODO: optimize this method to ge ProductPoint of ProductCart
        return new JPAQuery<ProductCart>(em)
                .from(qProductCart)

                .leftJoin(qProductCart.productPoint, qProductPoint).fetchJoin()
                .leftJoin(qProductPoint.enterprise).fetchJoin()
                .leftJoin(qProductPoint.product, qProduct).fetchJoin()
                .leftJoin(qProduct.productImages).fetchJoin()

                .where(condition)
                .fetch();

    }
}
