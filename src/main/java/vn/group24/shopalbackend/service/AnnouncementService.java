package vn.group24.shopalbackend.service;

import java.util.List;
import java.util.Map;

import vn.group24.shopalbackend.controller.request.AnnouncementSearchCriteriaRequest;
import vn.group24.shopalbackend.domain.Announcement;
import vn.group24.shopalbackend.domain.dto.AbstractAnn;
import vn.group24.shopalbackend.domain.dto.AnnouncementInput;

/**
 *
 * @author ttg
 */
public interface AnnouncementService {

    Announcement createAnnouncement(AnnouncementInput input);

    List<Announcement> getAnnouncementByCriteria(AnnouncementSearchCriteriaRequest criteria);

    Integer getNextAnnouncementMessageIdSeq();

    <T extends AbstractAnn> Map<Announcement, T> getAnnouncementMessageMap(List<Announcement> announcements);
}
