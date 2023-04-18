package vn.group24.shopalbackend.security.repository;

import java.util.List;

import vn.group24.shopalbackend.security.domain.UserAccountToken;

/**
 *
 * @author ttg
 */
public interface UserAccountTokenRepositoryCustom {
    List<UserAccountToken> findAllValidTokenByUser(Integer id);
}
