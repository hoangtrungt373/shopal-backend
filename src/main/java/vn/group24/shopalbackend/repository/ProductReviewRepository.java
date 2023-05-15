package vn.group24.shopalbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.ProductReview;
import vn.group24.shopalbackend.domain.enums.ProductReviewType;

/**
 * @author ttg
 */
@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {
    List<ProductReview> getByProductIdAndCustomerIdAndReviewType(Integer productId, Integer customerId, ProductReviewType reviewType);
}
