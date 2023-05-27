package vn.group24.shopalbackend.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.group24.shopalbackend.controller.request.AnnouncementSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.request.EmailDetailRequest;
import vn.group24.shopalbackend.controller.request.EnterpriseRegisterRequest;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.domain.Announcement;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.dto.AnnouncementInput;
import vn.group24.shopalbackend.domain.dto.EnterpriseRegisterRequestAnn;
import vn.group24.shopalbackend.domain.enums.AnnouncementInterface;
import vn.group24.shopalbackend.domain.enums.AnnouncementStatus;
import vn.group24.shopalbackend.domain.enums.AnnouncementType;
import vn.group24.shopalbackend.domain.enums.EnterpriseRegisterRequestStatus;
import vn.group24.shopalbackend.repository.AnnouncementRepository;
import vn.group24.shopalbackend.repository.EnterpriseRepository;
import vn.group24.shopalbackend.security.domain.UserAccount;
import vn.group24.shopalbackend.security.domain.enums.UserRole;
import vn.group24.shopalbackend.security.repository.UserAccountRepository;
import vn.group24.shopalbackend.service.AnnouncementService;
import vn.group24.shopalbackend.service.EmailService;
import vn.group24.shopalbackend.service.EnterpriseRegisterRequestService;
import vn.group24.shopalbackend.util.Constants;

/**
 *
 * @author ttg
 */
@Service
@Transactional
public class EnterpriseRegisterRequestServiceImpl implements EnterpriseRegisterRequestService {

    @Autowired
    private EnterpriseRepository enterpriseRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private ObjectMapper jsonObjectMapper;


    @Override
    public String handleReceiveEnterpriseRegisterRequest(EnterpriseRegisterRequest request) throws JsonProcessingException {
        //TODO: validate
        EnterpriseRegisterRequestAnn enterpriseRegisterRequestAnn = new EnterpriseRegisterRequestAnn();
        enterpriseRegisterRequestAnn.setId(announcementService.getNextAnnouncementMessageIdSeq());
        enterpriseRegisterRequestAnn.setFullName(request.getFullName());
        enterpriseRegisterRequestAnn.setPosition(request.getPosition());
        enterpriseRegisterRequestAnn.setWorkEmail(request.getWorkEmail());
        enterpriseRegisterRequestAnn.setPhoneNumber(request.getPhoneNumber());
        enterpriseRegisterRequestAnn.setEnterpriseAddress(request.getEnterpriseAddress());
        enterpriseRegisterRequestAnn.setEnterpriseName(request.getEnterpriseName());
        enterpriseRegisterRequestAnn.setEnterpriseWebsite(request.getEnterpriseWebsite());
        enterpriseRegisterRequestAnn.setRegisterRequestStatus(EnterpriseRegisterRequestStatus.RECEIVED);
        enterpriseRegisterRequestAnn.setRegisterRequestStatusDescription(EnterpriseRegisterRequestStatus.RECEIVED.getTextForCurrentLan());
        enterpriseRegisterRequestAnn.setRegisterDate(LocalDateTime.now());
        enterpriseRegisterRequestAnn.setTaxId(request.getTaxId());

        announcementService.createAnnouncement(AnnouncementInput.builder()
                .anInterface(AnnouncementInterface.ENTERPRISE_REGISTER_REQUEST)
                .type(AnnouncementType.RECEIVE)
                .status(AnnouncementStatus.VALID)
                .message(jsonObjectMapper.writeValueAsString(enterpriseRegisterRequestAnn))
                .build());
        return "Received register request for enterprise " + request.getEnterpriseName() + " successfully";
    }

    @Override
    public List<EnterpriseRegisterRequestAnn> getAllEnterpriseRegisterRequest() {
        Map<Announcement, EnterpriseRegisterRequestAnn> enterpriseRegisterRequestAnnMap = new HashMap<>();
        announcementService.getAnnouncementByCriteria(AnnouncementSearchCriteriaRequest.builder()
                        .anInterface(AnnouncementInterface.ENTERPRISE_REGISTER_REQUEST)
                        .type(AnnouncementType.RECEIVE)
                        .build())
                .forEach(announcement -> {
                    try {
                        EnterpriseRegisterRequestAnn messageDto = jsonObjectMapper.readValue(announcement.getMessage(), EnterpriseRegisterRequestAnn.class);
                        enterpriseRegisterRequestAnnMap.put(announcement, messageDto);
                    } catch (JsonProcessingException e) {
                        throw new IllegalArgumentException(
                                "Unable to deserialize the message for EnterpriseRegisterRequest");
                    }
                });

        Map<Integer, Enterprise> enterpriseMap = enterpriseRepository.findAllById(enterpriseRegisterRequestAnnMap.values().stream().map(EnterpriseRegisterRequestAnn::getEnterpriseId)
                        .filter(Objects::nonNull).toList())
                .stream().collect(Collectors.toMap(Enterprise::getId, Function.identity()));

        enterpriseRegisterRequestAnnMap.forEach((announcement, enterpriseRegisterRequestAnn) -> {
            enterpriseRegisterRequestAnn.setAnnouncementId(announcement.getId());
            enterpriseRegisterRequestAnn.setEnterprise(Optional.ofNullable(enterpriseMap.get(enterpriseRegisterRequestAnn.getEnterpriseId()))
                    .map(enterprise -> {
                        EnterpriseDto enterpriseDto = new EnterpriseDto();
                        enterpriseDto.setId(enterprise.getId());
                        enterpriseDto.setEnterpriseName(enterprise.getEnterpriseName());
                        enterpriseDto.setLogoUrl(enterprise.getLogoUrl());
                        return enterpriseDto;
                    }).orElseGet(() -> null));
        });

        return new ArrayList<>(enterpriseRegisterRequestAnnMap.values());
    }

    @Override
    public String handleAcceptEnterpriseCooperationRequest(EnterpriseRegisterRequestAnn enterpriseRegisterRequestAnn) {

        String tempPassword = "password";
//                StringUtils.generateOtp();
        String passwordEncode = passwordEncoder.encode(tempPassword);

        UserAccount userAccount = UserAccount.builder()
                .email(enterpriseRegisterRequestAnn.getWorkEmail())
                .password(passwordEncode)
                .role(UserRole.ENTERPRISE_MANAGER)
                .build();
        userAccountRepository.save(userAccount);

        Enterprise newEnterprise = new Enterprise();
        newEnterprise.setEnterpriseName(enterpriseRegisterRequestAnn.getEnterpriseName());
        newEnterprise.setAddress(enterpriseRegisterRequestAnn.getEnterpriseAddress());
        newEnterprise.setContactEmail(enterpriseRegisterRequestAnn.getWorkEmail());
        newEnterprise.setTaxId(enterpriseRegisterRequestAnn.getTaxId());
        newEnterprise.setJoinDate(LocalDate.now());
        newEnterprise.setUserAccountId(userAccount.getId());
        newEnterprise.setPhoneNumber(enterpriseRegisterRequestAnn.getPhoneNumber());
        newEnterprise.setWebsiteUrl(enterpriseRegisterRequestAnn.getEnterpriseWebsite());
        enterpriseRepository.save(newEnterprise);

        EmailDetailRequest emailDetailRequest = new EmailDetailRequest();
        emailDetailRequest.setRecipient(enterpriseRegisterRequestAnn.getWorkEmail());
        emailDetailRequest.setSubject("Create Enterprise Account OK");
        emailDetailRequest.setTemplate(Constants.ACCEPT_ENTERPRISE_COOPERATION_REQUEST_TEMPLATE);
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", enterpriseRegisterRequestAnn.getWorkEmail());
        properties.put("password", tempPassword);
        emailDetailRequest.setProperties(properties);
        emailService.sendEmail(emailDetailRequest);

        enterpriseRegisterRequestAnn.setRegisterRequestStatus(EnterpriseRegisterRequestStatus.ACCEPT);
        enterpriseRegisterRequestAnn.setTempPasswordGenerate(passwordEncode);
        enterpriseRegisterRequestAnn.setEnterpriseId(newEnterprise.getId());

        Announcement announcement = announcementRepository.findById(enterpriseRegisterRequestAnn.getAnnouncementId()).orElseGet(() -> null);
        Validate.isTrue(announcement != null, "Can not found Announcement with id = %s", enterpriseRegisterRequestAnn.getAnnouncementId());

        announcement.setEnterprise(newEnterprise);
        announcement.setStatus(AnnouncementStatus.TRANSMITTED);
        try {
            announcement.setMessage(jsonObjectMapper.writeValueAsString(enterpriseRegisterRequestAnn));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        announcementRepository.save(announcement);

        return "Accept register request for enterprise " + enterpriseRegisterRequestAnn.getEnterpriseName() + " successfully";
    }
}
