package vn.group24.shopalbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.enums.Language;
import vn.group24.shopalbackend.domain.multilingual.OrderStatusLan;

/**
 *
 * @author ttg
 */
@Repository
public interface OrderStatusLanRepository extends JpaRepository<OrderStatusLan, Integer> {

    List<OrderStatusLan> getByLanguage(Language language);
}
