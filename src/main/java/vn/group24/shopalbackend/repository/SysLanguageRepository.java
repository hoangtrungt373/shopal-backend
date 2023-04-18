package vn.group24.shopalbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.group24.shopalbackend.domain.multilingual.SysLanguage;

/**
 * @author ttg
 */
@Repository
public interface SysLanguageRepository extends JpaRepository<SysLanguage, Integer> {

    SysLanguage findByCodeAndActiveIsTrue(String code);
}
