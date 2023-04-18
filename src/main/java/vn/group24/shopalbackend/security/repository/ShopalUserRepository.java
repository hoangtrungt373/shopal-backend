package vn.group24.shopalbackend.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.security.domain.ShopalUser;

@Repository
public interface ShopalUserRepository extends JpaRepository<ShopalUser, Integer> {

    Optional<ShopalUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
