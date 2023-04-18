package vn.group24.shopalbackend.security.repository;

import java.util.List;

import vn.group24.shopalbackend.security.domain.ShopalToken;

/**
 *
 * @author ttg
 */
public interface ShopalTokenRepositoryCustom {
    List<ShopalToken> findAllValidTokenByUser(Integer id);
}
