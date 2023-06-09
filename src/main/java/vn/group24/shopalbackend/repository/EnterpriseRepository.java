package vn.group24.shopalbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.Enterprise;

/**
 *
 * @author ttg
 */
@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Integer>, EnterpriseRepositoryCustom {
    boolean existsByUserAccountId(Integer userAccountId);

    Enterprise getByUserAccountId(Integer userAccountId);
}
