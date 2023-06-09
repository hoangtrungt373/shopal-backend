package vn.group24.shopalbackend.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.ProductCart;

/**
 *
 * @author ttg
 */
@Repository
public interface ProductCartRepository extends JpaRepository<ProductCart, Integer>, ProductCartRepositoryCustom {
    ProductCart getByCustomerIdAndProductPointId(Integer customerId, Integer productPointId);

    List<ProductCart> getByProductPointIdIn(Collection<Integer> productPointIds);
};
