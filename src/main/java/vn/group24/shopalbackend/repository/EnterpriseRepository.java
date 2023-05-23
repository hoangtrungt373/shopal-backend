package vn.group24.shopalbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.Enterprise;

/**
 *
 * @author ttg
 */
@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Integer> {
    boolean existsByUserAccountId(Integer userAccountId);

    Enterprise getByUserAccountId(Integer userAccountId);

    @Query(value = "select next value for shop.ENTERPRISE_REGISTER_REQUEST_SEQ", nativeQuery = true)
    Integer getNextEnterpriseRequestId();
}
