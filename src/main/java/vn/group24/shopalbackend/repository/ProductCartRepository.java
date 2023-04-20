package vn.group24.shopalbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.ProductCart;

/**
 *
 * @author ttg
 */
@Repository
public interface ProductCartRepository extends JpaRepository<ProductCart, Integer>, ProductCartRepositoryCustom {
    ProductCart getByProductIdAndProductPointIdAndCustomerId(Integer productId, Integer productPointId, Integer customerId);
}
