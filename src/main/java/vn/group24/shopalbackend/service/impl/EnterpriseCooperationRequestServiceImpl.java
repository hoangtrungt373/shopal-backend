package vn.group24.shopalbackend.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.group24.shopalbackend.controller.request.EmailDetailRequest;
import vn.group24.shopalbackend.controller.request.EnterpriseRegisterRequest;
import vn.group24.shopalbackend.controller.response.admin.EnterpriseCooperationRequestDto;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.enums.EnterpriseRegisterRequestStatus;
import vn.group24.shopalbackend.domain.notification.EnterpriseCooperationRequest;
import vn.group24.shopalbackend.mapper.EnterpriseCooperationRequestMapper;
import vn.group24.shopalbackend.repository.EnterpriseCooperationRequestRepository;
import vn.group24.shopalbackend.repository.EnterpriseRepository;
import vn.group24.shopalbackend.service.EmailService;
import vn.group24.shopalbackend.service.EnterpriseCooperationRequestService;
import vn.group24.shopalbackend.util.Constants;

/**
 *
 * @author ttg
 */
@Service
@Transactional
public class EnterpriseCooperationRequestServiceImpl implements EnterpriseCooperationRequestService {

    @Autowired
    private EnterpriseCooperationRequestRepository enterpriseCooperationRequestRepository;
    @Autowired
    private EnterpriseRepository enterpriseRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EnterpriseCooperationRequestMapper enterpriseCooperationRequestMapper;


    @Override
    public String handleReceiveEnterpriseCooperationRequest(EnterpriseRegisterRequest request) {
        //TODO: validate
        EnterpriseCooperationRequest enterpriseCooperationRequest = new EnterpriseCooperationRequest();
        enterpriseCooperationRequest.setFullName(request.getFullName());
        enterpriseCooperationRequest.setPosition(request.getPosition());
        enterpriseCooperationRequest.setWorkEmail(request.getWorkEmail());
        enterpriseCooperationRequest.setPhoneNumber(request.getPhoneNumber());
        enterpriseCooperationRequest.setEnterpriseAddress(request.getEnterpriseAddress());
        enterpriseCooperationRequest.setEnterpriseName(request.getEnterpriseName());
        enterpriseCooperationRequest.setEnterpriseWebsite(request.getEnterpriseWebsite());
        enterpriseCooperationRequest.setRegisterRequestStatus(EnterpriseRegisterRequestStatus.RECEIVED);
        enterpriseCooperationRequest.setRegisterDate(LocalDateTime.now());
        enterpriseCooperationRequestRepository.save(enterpriseCooperationRequest);

        return "Received cooperation request for enterprise " + request.getEnterpriseName() + " successfully";
    }

    @Override
    public List<EnterpriseCooperationRequestDto> getAllEnterpriseCooperationRequest() {
        List<EnterpriseCooperationRequest> enterpriseCooperationRequests = enterpriseCooperationRequestRepository.findAll();

        Map<Integer, Enterprise> enterpriseMap = enterpriseRepository.findAllById(enterpriseCooperationRequests.stream().map(EnterpriseCooperationRequest::getEnterpriseId)
                        .filter(Objects::nonNull).toList())
                .stream().collect(Collectors.toMap(Enterprise::getId, Function.identity()));

        enterpriseCooperationRequests.forEach(enterpriseCooperationRequest -> {
            enterpriseCooperationRequest.setEnterprise(enterpriseMap.get(enterpriseCooperationRequest.getEnterpriseId()));
        });

        return enterpriseCooperationRequestMapper.mapToEnterpriseCooperationContractDto(enterpriseCooperationRequests);
    }

    @Override
    public String handleAcceptEnterpriseCooperationRequest(Integer requestId) {
        EnterpriseCooperationRequest enterpriseCooperationRequest = enterpriseCooperationRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("EnterpriseCooperationRequest not found with id = %s", requestId)));

        String tempPassword = "123456";
//                StringUtils.generateOtp();

        EmailDetailRequest emailDetailRequest = new EmailDetailRequest();
        emailDetailRequest.setRecipient(enterpriseCooperationRequest.getWorkEmail());
        emailDetailRequest.setSubject("Create Enterprise Account OK");
        emailDetailRequest.setTemplate(Constants.ACCEPT_ENTERPRISE_COOPERATION_REQUEST_TEMPLATE);
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", enterpriseCooperationRequest.getWorkEmail());
        properties.put("password", tempPassword);
        emailDetailRequest.setProperties(properties);
        emailService.sendEmail(emailDetailRequest);

        enterpriseCooperationRequest.setRegisterRequestStatus(EnterpriseRegisterRequestStatus.ACCEPT);
        enterpriseCooperationRequest.setTempPasswordGenerate(passwordEncoder.encode(passwordEncoder.encode(tempPassword)));
        enterpriseCooperationRequestRepository.save(enterpriseCooperationRequest);

        return "Accept cooperation request for enterprise " + enterpriseCooperationRequest.getEnterpriseName() + " successfully";
    }
}
