package vn.group24.shopalbackend.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vn.group24.shopalbackend.controller.request.CustomerSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.request.EmailDetailRequest;
import vn.group24.shopalbackend.controller.response.admin.CustomerAllInfoDto;
import vn.group24.shopalbackend.controller.response.common.CustomerDto;
import vn.group24.shopalbackend.controller.response.enterprise.CustomerMembershipDto;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.mapper.CustomerMapper;
import vn.group24.shopalbackend.mapper.CustomerMembershipMapper;
import vn.group24.shopalbackend.mapper.admin.CustomerAllInfoMapper;
import vn.group24.shopalbackend.repository.CustomerRepository;
import vn.group24.shopalbackend.repository.EnterpriseRepository;
import vn.group24.shopalbackend.security.domain.UserAccount;
import vn.group24.shopalbackend.service.CustomerService;
import vn.group24.shopalbackend.service.EmailService;
import vn.group24.shopalbackend.util.Constants;
import vn.group24.shopalbackend.util.FileUtils;
import vn.group24.shopalbackend.util.StringUtils;

/**
 * @author ttg
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private CustomerMembershipMapper customerMembershipMapper;
    @Autowired
    private CustomerAllInfoMapper customerAllInfoMapper;

    @Override
    public CustomerDto getCustomerInfo(UserAccount userAccount) {
        Customer customer = customerRepository.getByUserAccountId(userAccount.getId());
        return customerMapper.mapToCustomerDto(customer);
    }

    @Override
    public List<CustomerAllInfoDto> getCustomerAllInfoByCriteria(CustomerSearchCriteriaRequest request) {
        // TODO: fetch join by criteria
        return customerAllInfoMapper.mapToCustomerAllInfoDtos(customerRepository.getByCriteria(request));
    }

    @Override
    public String updateCustomerInfo(Customer customer, CustomerDto request, MultipartFile uploadAvatarUrl) throws IOException {
        //TODO: validate field
        customer.setAddress(request.getAddress());
        customer.setBirthDate(request.getBirthDate());
        customer.setFullName(request.getFullName());
        customer.setGender(request.getGender());
        customer.setNickName(request.getNickName());
        if (!Objects.equals(customer.getPhoneNumber(), request.getPhoneNumber())) {
            customer.setPhoneNumber(request.getPhoneNumber());
        }
        if (uploadAvatarUrl != null) {
            customer.setAvatarUrl(FileUtils.saveFileWithRandomName(uploadAvatarUrl, Constants.CUSTOMER_AVATAR_DIRECTORY));
        }
        customerRepository.save(customer);
        return "Update customer info successfully";
    }

    @Override
    public String handleSendEmailVerifyEmailUpdate(Customer customer, String newEmail) {
        // TODO: validate new email
        String otp = StringUtils.generateOtp();

        EmailDetailRequest emailDetailRequest = new EmailDetailRequest();
        emailDetailRequest.setRecipient(newEmail);
        emailDetailRequest.setSubject("Change email address");
        emailDetailRequest.setTemplate(Constants.VERIFY_EMAIL_TEMPLATE);
        Map<String, Object> properties = new HashMap<>();
        properties.put("code", otp);
        emailDetailRequest.setProperties(properties);
        emailService.sendEmail(emailDetailRequest);
        return otp;
    }

    @Override
    public List<CustomerMembershipDto> getCustomerMembershipForEnterprise(UserAccount userAccount) {
        //TODO: fetch join here
        Enterprise enterprise = enterpriseRepository.getByUserAccountId(userAccount.getId());
        return customerMembershipMapper.mapToCustomerMembershipDtos(enterprise);
    }

}
