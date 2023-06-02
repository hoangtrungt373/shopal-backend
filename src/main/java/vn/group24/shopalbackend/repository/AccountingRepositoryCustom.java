package vn.group24.shopalbackend.repository;

import java.util.List;

import vn.group24.shopalbackend.controller.request.AccountingSearchCriteriaRequest;
import vn.group24.shopalbackend.domain.Accounting;

/**
 *
 * @author ttg
 */
public interface AccountingRepositoryCustom {
    List<Accounting> getByCriteria(AccountingSearchCriteriaRequest criteria);
}
