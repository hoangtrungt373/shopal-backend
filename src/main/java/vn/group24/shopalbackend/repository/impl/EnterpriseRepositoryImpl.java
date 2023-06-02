package vn.group24.shopalbackend.repository.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import vn.group24.shopalbackend.controller.request.EnterpriseSearchCriteriaRequest;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.QEnterprise;
import vn.group24.shopalbackend.repository.EnterpriseRepositoryCustom;

public class EnterpriseRepositoryImpl implements EnterpriseRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    QEnterprise qEnterprise = new QEnterprise("qEnterprise");

    @Override
    public List<Enterprise> getByCriteria(EnterpriseSearchCriteriaRequest criteria) {

        BooleanExpression condition = qEnterprise.id.isNotNull();

        if (StringUtils.isNotBlank(criteria.getContactEmail())) {
            condition = condition.and(qEnterprise.contactEmail.containsIgnoreCase(criteria.getContactEmail()));
        }

        if (StringUtils.isNotBlank(criteria.getPhoneNumber())) {
            condition = condition.and(qEnterprise.phoneNumber.eq(criteria.getPhoneNumber()));
        }

        if (StringUtils.isNotBlank(criteria.getAddress())) {
            condition = condition.and(qEnterprise.address.containsIgnoreCase(criteria.getAddress()));
        }

        if (StringUtils.isNotBlank(criteria.getTaxId())) {
            condition = condition.and(qEnterprise.taxId.eq(criteria.getTaxId()));
        }

        return new JPAQuery<Enterprise>(em)
                .from(qEnterprise)
                .where(condition)
                .fetch();
    }

}
