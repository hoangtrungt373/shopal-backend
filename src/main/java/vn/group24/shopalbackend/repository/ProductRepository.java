package vn.group24.shopalbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.Product;

/**
 * @author ttg
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, ProductRepositoryCustom {
    Product getBySku(String sku);

    @Query(value = "select next value for shop.PRODUCT_SKU_SEQ", nativeQuery = true)
    String getNextSku();
}
