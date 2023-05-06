package vn.group24.shopalbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.Accounting;

/**
 *
 * @author ttg
 */
@Repository
public interface AccountingRepository extends JpaRepository<Accounting, Integer>, AccountingRepositoryCustom {

    List<Accounting> getByEnterpriseId(Integer enterpriseId);
}
