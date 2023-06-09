package vn.group24.shopalbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.ProductPoint;

/**
 * @author ttg
 */
@Repository
public interface ProductPointRepository extends JpaRepository<ProductPoint, Integer> {
    ProductPoint getByIdAndActiveIsTrue(Integer productPointId);

    ProductPoint getByEnterpriseIdAndProductIdAndActiveIsTrue(Integer enterpriseId, Integer productId);

    List<ProductPoint> getByEnterpriseIdAndActiveIsTrue(Integer enterpriseId);
}
