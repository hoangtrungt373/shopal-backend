package vn.group24.shopalbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.Membership;

/**
 *
 * @author ttg
 */
@Repository
public interface MembershipRepository extends JpaRepository<Membership, Integer> {
    List<Membership> getByCustomerId(Integer customerId);
}
