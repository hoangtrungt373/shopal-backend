package vn.group24.shopalbackend.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.security.domain.UserAccountToken;

@Repository
public interface UserAccountTokenRepository extends JpaRepository<UserAccountToken, Integer>, UserAccountTokenRepositoryCustom {

    List<UserAccountToken> findByToken(String token);
}
