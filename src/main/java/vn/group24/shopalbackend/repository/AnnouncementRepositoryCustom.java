package vn.group24.shopalbackend.repository;

import java.util.List;

import vn.group24.shopalbackend.controller.request.AnnouncementSearchCriteriaRequest;
import vn.group24.shopalbackend.domain.Announcement;

/**
 *
 * @author ttg
 */
public interface AnnouncementRepositoryCustom {
    List<Announcement> getByCriteria(AnnouncementSearchCriteriaRequest criteria);
}
