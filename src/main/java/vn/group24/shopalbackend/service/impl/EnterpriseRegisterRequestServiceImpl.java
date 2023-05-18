package vn.group24.shopalbackend.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.group24.shopalbackend.controller.request.EmailDetailRequest;
import vn.group24.shopalbackend.controller.request.EnterpriseRegisterRequest;
import vn.group24.shopalbackend.controller.response.admin.EnterpriseRegisterRequestAnnDto;
import vn.group24.shopalbackend.domain.dto.AnnouncementInput;
import vn.group24.shopalbackend.domain.dto.EnterpriseRegisterRequestAnn;
import vn.group24.shopalbackend.domain.enums.AnnouncementInterface;
import vn.group24.shopalbackend.domain.enums.AnnouncementStatus;
import vn.group24.shopalbackend.domain.enums.AnnouncementType;
import vn.group24.shopalbackend.domain.enums.EnterpriseRegisterRequestStatus;
import vn.group24.shopalbackend.mapper.EnterpriseRegisterRequestMapper;
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
    private EnterpriseRegisterRequestMapper enterpriseRegisterRequestMapper;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private ObjectMapper jsonObjectMapper;


    @Override
    public String handleReceiveEnterpriseRegisterRequest(EnterpriseRegisterRequest request) throws JsonProcessingException {
        //TODO: validate
        EnterpriseRegisterRequestAnn enterpriseRegisterRequestAnn = new EnterpriseRegisterRequestAnn();
        enterpriseRegisterRequestAnn.setFullName(request.getFullName());
        enterpriseRegisterRequestAnn.setPosition(request.getPosition());
        enterpriseRegisterRequestAnn.setWorkEmail(request.getWorkEmail());
        enterpriseRegisterRequestAnn.setPhoneNumber(request.getPhoneNumber());
        enterpriseRegisterRequestAnn.setEnterpriseAddress(request.getEnterpriseAddress());
        enterpriseRegisterRequestAnn.setEnterpriseName(request.getEnterpriseName());
        enterpriseRegisterRequestAnn.setEnterpriseWebsite(request.getEnterpriseWebsite());
        enterpriseRegisterRequestAnn.setRegisterRequestStatus(EnterpriseRegisterRequestStatus.RECEIVED);
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
    public List<EnterpriseRegisterRequestAnnDto> getAllEnterpriseRegisterRequest() {
        return null;
//        List<EnterpriseRegisterRequestAnn> enterpriseRegisterRequestAnns =
//
//        Map<Integer, Enterprise> enterpriseMap = enterpriseRepository.findAllById(enterpriseRegisterRequestAnns.stream().map(EnterpriseCooperationRequest::getEnterpriseId)
//                        .filter(Objects::nonNull).toList())
//                .stream().collect(Collectors.toMap(Enterprise::getId, Function.identity()));
//
//        enterpriseRegisterRequestAnns.forEach(enterpriseCooperationRequest -> {
//            enterpriseCooperationRequest.setEnterprise(enterpriseMap.get(enterpriseCooperationRequest.getEnterpriseId()));
//        });
//
//        return enterpriseRegisterRequestMapper.mapToEnterpriseRegisterRequestAnnDto(enterpriseRegisterRequestAnns);
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
        enterpriseRegisterRequestAnn.setTempPasswordGenerate(passwordEncoder.encode(passwordEncoder.encode(tempPassword)));
//        enterpriseCooperationRequestRepository.save(enterpriseRegisterRequestAnnouncement);

        return "Accept cooperation request for enterprise " + enterpriseRegisterRequestAnn.getEnterpriseName() + " successfully";
    }
}
