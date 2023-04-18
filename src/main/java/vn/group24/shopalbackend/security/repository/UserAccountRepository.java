package vn.group24.shopalbackend.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.security.domain.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

    Optional<UserAccount> findByEmail(String email);

    boolean existsByEmail(String email);
}
