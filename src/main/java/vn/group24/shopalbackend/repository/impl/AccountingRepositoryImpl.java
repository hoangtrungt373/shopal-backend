package vn.group24.shopalbackend.repository.impl;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import vn.group24.shopalbackend.controller.request.AccountingSearchCriteriaRequest;
import vn.group24.shopalbackend.domain.Accounting;
import vn.group24.shopalbackend.domain.QAccounting;
import vn.group24.shopalbackend.domain.QEnterprise;
import vn.group24.shopalbackend.repository.AccountingRepositoryCustom;

public class AccountingRepositoryImpl implements AccountingRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Accounting> getByCriteria(AccountingSearchCriteriaRequest criteria) {
        QAccounting qAccounting = new QAccounting("qAccounting");
        QEnterprise qEnterprise = new QEnterprise("qEnterprise");

        BooleanExpression condition = qAccounting.id.isNotNull();

        if (criteria.getEnterpriseId() != null) {
            condition = condition.and(qEnterprise.id.eq(criteria.getEnterpriseId()));
        }

        if (criteria.getStartDate() != null) {
            condition = condition.and(qAccounting.startDate.goe(criteria.getStartDate()));
        }

        if (criteria.getEndDate() != null) {
            condition = condition.and(qAccounting.endDate.loe(criteria.getEndDate()));
        }

        if (criteria.getPaymentStatus() != null) {
            condition = condition.and(qAccounting.paymentStatus.eq(criteria.getPaymentStatus()));
        }

        return new JPAQuery<Accounting>(em)
                .from(qAccounting)
                .leftJoin(qAccounting.enterprise, qEnterprise).fetchJoin()
                .where(condition)
                .fetch();
    }

}
