package vn.group24.shopalbackend.security.repository.impl;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import vn.group24.shopalbackend.security.domain.QUserAccount;
import vn.group24.shopalbackend.security.domain.QUserAccountToken;
import vn.group24.shopalbackend.security.domain.UserAccountToken;
import vn.group24.shopalbackend.security.repository.UserAccountTokenRepositoryCustom;


/**
 *
 * @author ttg
 */
public class UserAccountTokenRepositoryImpl implements UserAccountTokenRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<UserAccountToken> findAllValidTokenByUser(Integer id) {
        QUserAccountToken qUserAccountToken = QUserAccountToken.userAccountToken;
        QUserAccount qUserAccount = QUserAccount.userAccount;

        BooleanExpression condition = qUserAccount.id.eq(id)
                .and(qUserAccountToken.revoked.isFalse().or(qUserAccountToken.expired.isFalse()));

        return new JPAQuery<UserAccountToken>(em)
                .from(qUserAccountToken)
                .join(qUserAccountToken.userAccount, qUserAccount)
                .where(condition)
                .fetch();
    }
}
