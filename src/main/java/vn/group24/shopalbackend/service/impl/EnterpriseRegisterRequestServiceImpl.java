package vn.group24.shopalbackend.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import vn.group24.shopalbackend.repository.EnterpriseRepository;
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
        enterpriseRegisterRequestAnn.setId(enterpriseRepository.getNextEnterpriseRequestId());
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
        List<EnterpriseRegisterRequestAnn> enterpriseRegisterRequestAnns = announcementService.getAnnouncementByCriteria(AnnouncementSearchCriteriaRequest.builder()
                        .anInterface(AnnouncementInterface.ENTERPRISE_REGISTER_REQUEST)
                        .build())
                .stream()
                .map(Announcement::getMessage)
                .map(message -> {
                    try {
                        return jsonObjectMapper.readValue(message, EnterpriseRegisterRequestAnn.class);
                    } catch (JsonProcessingException e) {
                        throw new IllegalArgumentException(
                                "Unable to deserialize the message for EnterpriseRegisterRequest");
                    }
                })
                .collect(Collectors.toList());

        Map<Integer, Enterprise> enterpriseMap = enterpriseRepository.findAllById(enterpriseRegisterRequestAnns.stream().map(EnterpriseRegisterRequestAnn::getEnterpriseId)
                        .filter(Objects::nonNull).toList())
                .stream().collect(Collectors.toMap(Enterprise::getId, Function.identity()));

        enterpriseRegisterRequestAnns.forEach(announcement -> {
            announcement.setEnterprise(Optional.ofNullable(enterpriseMap.get(announcement.getEnterpriseId()))
                    .map(enterprise -> {
                        EnterpriseDto enterpriseDto = new EnterpriseDto();
                        enterpriseDto.setId(enterprise.getId());
                        enterpriseDto.setEnterpriseName(enterprise.getEnterpriseName());
                        enterpriseDto.setLogoUrl(enterprise.getLogoUrl());
                        return enterpriseDto;
                    }).orElseGet(() -> null));
        });

        return enterpriseRegisterRequestAnns;
    }

    @Override
    public String handleAcceptEnterpriseCooperationRequest(Integer requestId) {
        EnterpriseRegisterRequestAnn enterpriseRegisterRequestAnn = null;
//        orElseThrow(() -> new IllegalArgumentException(String.format("EnterpriseCooperationRequest not found with id = %s", requestId)));

        String tempPassword = "123456";
//                StringUtils.generateOtp();

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
        enterpriseRegisterRequestAnn.setRegisterRequestStatusDescription(EnterpriseRegisterRequestStatus.ACCEPT.getTextForCurrentLan());
        enterpriseRegisterRequestAnn.setTempPasswordGenerate(passwordEncoder.encode(passwordEncoder.encode(tempPassword)));
//        enterpriseCooperationRequestRepository.save(enterpriseRegisterRequestAnnouncement);

        return "Accept cooperation request for enterprise " + enterpriseRegisterRequestAnn.getEnterpriseName() + " successfully";
    }
}
