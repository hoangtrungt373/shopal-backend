package vn.group24.shopalbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.ProductPoint;

/**
 * @author ttg
 */
@Repository
public interface ProductPointRepository extends JpaRepository<ProductPoint, Integer> {
}
