package vn.group24.shopalbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.staging.StagCustomer;

/**
 *
 * @author ttg
 */
@Repository
public interface StagCustomerRepository extends JpaRepository<StagCustomer, Integer> {
    StagCustomer getByRegisterEmailAndRegisterPhoneNumber(String registerEmail, String registerPhoneNumber);

    List<StagCustomer> getByEnterpriseId(Integer enterpriseId);
}
