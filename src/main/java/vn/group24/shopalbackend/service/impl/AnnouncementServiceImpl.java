package vn.group24.shopalbackend.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.group24.shopalbackend.controller.request.AnnouncementSearchCriteriaRequest;
import vn.group24.shopalbackend.domain.Announcement;
import vn.group24.shopalbackend.domain.dto.AnnouncementInput;
import vn.group24.shopalbackend.repository.AnnouncementRepository;
import vn.group24.shopalbackend.service.AnnouncementService;

/**
 *
 * @author ttg
 */
@Service
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Override
    public Announcement createAnnouncement(AnnouncementInput input) {
        Announcement reqAnnouncement = new Announcement();
        reqAnnouncement.setProduct(input.getProduct());
        reqAnnouncement.setEnterprise(input.getEnterprise());
        reqAnnouncement.setAnInterface(input.getAnInterface());
        reqAnnouncement.setType(input.getType());
        reqAnnouncement.setStatus(input.getStatus());
        reqAnnouncement.setDate(LocalDateTime.now());
        reqAnnouncement.setMessage(input.getMessage());
        reqAnnouncement.setHash(UUID.randomUUID().toString());
        return announcementRepository.save(reqAnnouncement);
    }

    @Override
    public List<Announcement> getAnnouncementByCriteria(AnnouncementSearchCriteriaRequest criteria) {
        return announcementRepository.getByCriteria(criteria);
    }
}
