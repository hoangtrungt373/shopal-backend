package vn.group24.shopalbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.PurchaseOrder;

/**
 *
 * @author ttg
 */
@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
    List<PurchaseOrder> getByCustomerId(Integer customerId);

    List<PurchaseOrder> getByEnterpriseId(Integer enterpriseId);
}
