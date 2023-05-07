package vn.group24.shopalbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.Catalog;

/**
 * @author ttg
 */
@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Integer> {
    List<Catalog> getByLevel(Integer level);
}
