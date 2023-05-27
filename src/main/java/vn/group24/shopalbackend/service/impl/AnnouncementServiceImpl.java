package vn.group24.shopalbackend.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.group24.shopalbackend.controller.request.AnnouncementSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.domain.Announcement;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.dto.AbstractAnn;
import vn.group24.shopalbackend.domain.dto.AnnouncementInput;
import vn.group24.shopalbackend.repository.AnnouncementRepository;
import vn.group24.shopalbackend.repository.EnterpriseRepository;
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
    @Autowired
    private ObjectMapper jsonObjectMapper;
    @Autowired
    private EnterpriseRepository enterpriseRepository;

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

    @Override
    public Integer getNextAnnouncementMessageIdSeq() {
        return announcementRepository.getNextAnnouncementMessageIdSeq();
    }

    @Override
    public <T extends AbstractAnn> Map<Announcement, T> getAnnouncementMessageMap(List<Announcement> announcements) {

        Map<Announcement, AbstractAnn> announcementMap = new HashMap<>();
        announcements.forEach(announcement -> {
            try {
                AbstractAnn messageDto =
                        jsonObjectMapper.readValue(announcement.getMessage(), AbstractAnn.class);
                announcementMap.put(announcement, messageDto);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException(
                        "Unable to deserialize the message for CreateOrUpdateContractRequestAnn");
            }
        });

        Map<Integer, Enterprise> enterpriseMap = enterpriseRepository.findAllById(announcementMap.values().stream().map(AbstractAnn::getEnterpriseId)
                        .filter(Objects::nonNull).toList())
                .stream().collect(Collectors.toMap(Enterprise::getId, Function.identity()));

        announcementMap.forEach((announcement, messageDto) -> {
            messageDto.setAnnouncementId(announcement.getId());
            messageDto.setDate(announcement.getDate());
            messageDto.setEnterprise(Optional.ofNullable(enterpriseMap.get(messageDto.getEnterpriseId()))
                    .map(enterprise -> {
                        EnterpriseDto enterpriseDto = new EnterpriseDto();
                        enterpriseDto.setId(enterprise.getId());
                        enterpriseDto.setEnterpriseName(enterprise.getEnterpriseName());
                        enterpriseDto.setLogoUrl(enterprise.getLogoUrl());
                        return enterpriseDto;
                    }).orElseGet(() -> null));
        });

        return (Map<Announcement, T>) announcementMap;
    }
}
