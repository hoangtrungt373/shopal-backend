package vn.group24.shopalbackend.repository.impl;

import java.util.List;

import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import vn.group24.shopalbackend.domain.Accounting;
import vn.group24.shopalbackend.domain.QAccounting;
import vn.group24.shopalbackend.repository.AccountingRepositoryCustom;

public class AccountingRepositoryImpl implements AccountingRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Accounting> getByCriteria(Integer enterpriseId) {
        QAccounting qAccounting = QAccounting.accounting;

        return new JPAQuery<Accounting>(em)
                .from(qAccounting)
                .fetch();
    }

}
