package vn.group24.shopalbackend.repository.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import vn.group24.shopalbackend.controller.request.AnnouncementSearchCriteriaRequest;
import vn.group24.shopalbackend.domain.Announcement;
import vn.group24.shopalbackend.domain.QAnnouncement;
import vn.group24.shopalbackend.repository.AnnouncementRepositoryCustom;

/**
 *
 * @author ttg
 */
public class AnnouncementRepositoryImpl implements AnnouncementRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    QAnnouncement qAnnouncement = new QAnnouncement("qAnnouncement");

    @Override
    public List<Announcement> getByCriteria(AnnouncementSearchCriteriaRequest criteria) {

        BooleanExpression condition = qAnnouncement.date.loe(LocalDateTime.now());

        if (criteria.getAnnouncementId() != null) {
            condition = condition.and(qAnnouncement.id.eq(criteria.getAnnouncementId()));
        }

        if (criteria.getAnInterface() != null) {
            condition = condition.and(qAnnouncement.anInterface.eq(criteria.getAnInterface()));
        }

        if (criteria.getStatus() != null) {
            condition = condition.and(qAnnouncement.status.eq(criteria.getStatus()));
        }

        if (criteria.getType() != null) {
            condition = condition.and(qAnnouncement.type.eq(criteria.getType()));
        }

        if (criteria.getProductId() != null) {
            condition = condition.and(qAnnouncement.product.id.eq(criteria.getProductId()));
        }

        if (criteria.getEnterpriseId() != null) {
            condition = condition.and(qAnnouncement.enterprise.id.eq(criteria.getEnterpriseId()));
        }

        return new JPAQuery<Announcement>(em)
                .from(qAnnouncement)
                .where(condition)
                .fetch();
    }
}
