package vn.group24.shopalbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Product;

/**
 * @author ttg
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, ProductRepositoryCustom {
}
