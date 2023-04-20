package vn.group24.shopalbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.Customer;

/**
 * @author ttg
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByUserAccountId(Integer userAccountId);

    Customer getByUserAccountId(Integer userId);
}
