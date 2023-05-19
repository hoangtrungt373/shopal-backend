package vn.group24.shopalbackend.repository;

import java.util.List;

import vn.group24.shopalbackend.controller.request.PurchaseOrderSearchCriteriaRequest;
import vn.group24.shopalbackend.domain.PurchaseOrder;

/**
 *
 * @author ttg
 */
public interface PurchaseOrderRepositoryCustom {
    List<PurchaseOrder> getByCriteria(PurchaseOrderSearchCriteriaRequest criteria);
}
