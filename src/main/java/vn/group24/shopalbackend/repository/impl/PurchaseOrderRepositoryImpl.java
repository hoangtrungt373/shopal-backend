package vn.group24.shopalbackend.repository.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import vn.group24.shopalbackend.controller.request.PurchaseOrderSearchCriteriaRequest;
import vn.group24.shopalbackend.domain.PurchaseOrder;
import vn.group24.shopalbackend.domain.QCustomer;
import vn.group24.shopalbackend.domain.QEnterprise;
import vn.group24.shopalbackend.domain.QPurchaseOrder;
import vn.group24.shopalbackend.repository.PurchaseOrderRepositoryCustom;

/**
 *
 * @author ttg
 */

public class PurchaseOrderRepositoryImpl implements PurchaseOrderRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    QPurchaseOrder qPurchaseOrder = new QPurchaseOrder("qPurchaseOrder");
    QEnterprise qEnterprise = new QEnterprise("qEnterprise");
    QCustomer qCustomer = new QCustomer("qCustomer");

    @Override
    public List<PurchaseOrder> getByCriteria(PurchaseOrderSearchCriteriaRequest criteria) {

        BooleanExpression condition = qPurchaseOrder.id.isNotNull();

        if (criteria.getPurchaseOrderId() != null) {
            condition = condition.and(qPurchaseOrder.id.eq(criteria.getPurchaseOrderId()));
        }

        if (criteria.getOrderDateFrom() != null) {
            condition = condition.and(qPurchaseOrder.orderDate.goe(criteria.getOrderDateFrom()));
        }

        if (criteria.getOrderDateTo() != null) {
            condition = condition.and(qPurchaseOrder.orderDate.loe(criteria.getOrderDateTo()));
        }

        if (criteria.getOrderStatus() != null) {
            condition = condition.and(qPurchaseOrder.orderStatus.eq(criteria.getOrderStatus()));
        }

        if (CollectionUtils.isNotEmpty(criteria.getEnterpriseIds())) {
            condition = condition.and(qEnterprise.id.in(criteria.getEnterpriseIds()));
        }

        if (criteria.getCustomerId() != null) {
            condition = condition.and(qCustomer.id.eq(criteria.getCustomerId()));
        }

        if (StringUtils.isNotBlank(criteria.getCustomerContactEmail())) {
            condition = condition.and(qCustomer.contactEmail.containsIgnoreCase(criteria.getCustomerContactEmail()));
        }

        if (StringUtils.isNotBlank(criteria.getCustomerPhoneNumber())) {
            condition = condition.and(qCustomer.phoneNumber.eq(criteria.getCustomerPhoneNumber()));
        }

        return new JPAQuery<PurchaseOrder>(em)
                .from(qPurchaseOrder)
                .leftJoin(qPurchaseOrder.enterprise, qEnterprise).fetchJoin()
                .leftJoin(qPurchaseOrder.customer, qCustomer).fetchJoin()
                .leftJoin(qPurchaseOrder.purchaseOrderDetails).fetchJoin()
                .where(condition)
                .fetch();
    }
}
