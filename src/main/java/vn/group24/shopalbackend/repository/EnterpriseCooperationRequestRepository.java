package vn.group24.shopalbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.notification.EnterpriseCooperationRequest;

/**
 *
 * @author ttg
 */
@Repository
public interface EnterpriseCooperationRequestRepository extends JpaRepository<EnterpriseCooperationRequest, Integer> {
}
