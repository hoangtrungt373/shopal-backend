package vn.group24.shopalbackend.repository.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import vn.group24.shopalbackend.controller.request.CustomerSearchCriteriaRequest;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.Membership;
import vn.group24.shopalbackend.domain.QCustomer;
import vn.group24.shopalbackend.domain.QEnterprise;
import vn.group24.shopalbackend.domain.QMembership;
import vn.group24.shopalbackend.repository.CustomerRepositoryCustom;

/**
 *
 * @author ttg
 */
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    QCustomer qCustomer = new QCustomer("qCustomer");
    QMembership qMembership = new QMembership("qMembership");
    QEnterprise qEnterprise = new QEnterprise("qEnterprise");

    @Override
    public List<Customer> getByCriteria(CustomerSearchCriteriaRequest criteria) {

        BooleanExpression condition = qCustomer.id.isNotNull();

        if (criteria.getCustomerId() != null) {
            condition = condition.and(qCustomer.id.eq(criteria.getCustomerId()));
        }

        if (StringUtils.isNotBlank(criteria.getCustomerEmail())) {
            condition = condition.and(qCustomer.contactEmail.containsIgnoreCase(criteria.getCustomerEmail()));
        }

        if (StringUtils.isNotBlank(criteria.getCustomerName())) {
            condition = condition.and(qCustomer.fullName.containsIgnoreCase(criteria.getCustomerName()));
        }

        if (StringUtils.isNotBlank(criteria.getCustomerPhoneNumber())) {
            condition = condition.and(qCustomer.phoneNumber.containsIgnoreCase(criteria.getCustomerPhoneNumber()));
        }

        JPAQuery<Customer> query = new JPAQuery<Customer>(em)
                .from(qCustomer)
                .leftJoin(qCustomer.memberships, qMembership).fetchJoin()
                .leftJoin(qMembership.enterprise).fetchJoin()
                .where(condition);

        List<Customer> customers = query.fetch();

        if (CollectionUtils.isNotEmpty(criteria.getAssociateEnterpriseIds())) {
            customers = customers.stream().filter(c -> {
                List<Integer> enterpriseIds = c.getMemberships().stream().map(Membership::getEnterprise).map(Enterprise::getId).toList();
                return criteria.getAssociateEnterpriseIds().stream().anyMatch(enterpriseIds::contains);
            }).toList();
        }

        return customers;
    }
}
