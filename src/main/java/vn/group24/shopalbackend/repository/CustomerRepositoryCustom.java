package vn.group24.shopalbackend.repository;

import java.util.List;

import vn.group24.shopalbackend.controller.request.CustomerSearchCriteriaRequest;
import vn.group24.shopalbackend.domain.Customer;

/**
 *
 * @author ttg
 */
public interface CustomerRepositoryCustom {
    List<Customer> getByCriteria(CustomerSearchCriteriaRequest criteria);

}
