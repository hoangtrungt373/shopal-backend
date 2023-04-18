package vn.group24.shopalbackend.security.repository.impl;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import vn.group24.shopalbackend.security.domain.QShopalToken;
import vn.group24.shopalbackend.security.domain.QShopalUser;
import vn.group24.shopalbackend.security.domain.ShopalToken;
import vn.group24.shopalbackend.security.repository.ShopalTokenRepositoryCustom;


/**
 *
 * @author ttg
 */
public class ShopalTokenRepositoryImpl implements ShopalTokenRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ShopalToken> findAllValidTokenByUser(Integer id) {
        QShopalToken qShopalToken = QShopalToken.shopalToken;
        QShopalUser qShopalUser = QShopalUser.shopalUser;

        BooleanExpression condition = qShopalUser.id.eq(id)
                .and(qShopalToken.revoked.isFalse().or(qShopalToken.expired.isFalse()));

        return new JPAQuery<ShopalToken>(em)
                .from(qShopalToken)
                .join(qShopalToken.user, qShopalUser)
                .where(condition)
                .fetch();
    }
}
