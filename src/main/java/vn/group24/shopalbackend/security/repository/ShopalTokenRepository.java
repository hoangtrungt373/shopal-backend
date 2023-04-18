package vn.group24.shopalbackend.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.security.domain.ShopalToken;

@Repository
public interface ShopalTokenRepository extends JpaRepository<ShopalToken, Integer>, ShopalTokenRepositoryCustom {

    Optional<ShopalToken> findByToken(String token);
}
