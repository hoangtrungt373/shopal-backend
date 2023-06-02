package vn.group24.shopalbackend.repository;

import java.util.List;

import vn.group24.shopalbackend.controller.request.EnterpriseSearchCriteriaRequest;
import vn.group24.shopalbackend.domain.Enterprise;

/**
 *
 * @author ttg
 */
public interface EnterpriseRepositoryCustom {
    List<Enterprise> getByCriteria(EnterpriseSearchCriteriaRequest criteria);
}
