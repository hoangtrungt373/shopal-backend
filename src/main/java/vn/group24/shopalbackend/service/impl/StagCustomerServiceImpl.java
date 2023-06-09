package vn.group24.shopalbackend.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.group24.shopalbackend.controller.request.CustomerNewMembershipRequest;
import vn.group24.shopalbackend.controller.response.enterprise.CustomerRegisterDto;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.Membership;
import vn.group24.shopalbackend.domain.staging.StagCustomer;
import vn.group24.shopalbackend.mapper.CustomerMembershipMapper;
import vn.group24.shopalbackend.repository.EnterpriseRepository;
import vn.group24.shopalbackend.repository.MembershipRepository;
import vn.group24.shopalbackend.repository.StagCustomerRepository;
import vn.group24.shopalbackend.security.domain.UserAccount;
import vn.group24.shopalbackend.service.StagCustomerService;
import vn.group24.shopalbackend.util.Validator;

/**
 *
 * @author ttg
 */
@Service
@Transactional
public class StagCustomerServiceImpl implements StagCustomerService {

    @Autowired
    private StagCustomerRepository stagCustomerRepository;
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private CustomerMembershipMapper customerMembershipMapper;

    @Override
    public String handleRequestNewMembershipForCustomer(Customer customer, CustomerNewMembershipRequest createNewMembershipRequest) {
        String registerEmail = createNewMembershipRequest.getRegisterEmail();
        String registerPhoneNumber = createNewMembershipRequest.getRegisterPhoneNumber();
        Integer enterpriseId = createNewMembershipRequest.getEnterpriseId();

        List<StagCustomer> registerAccounts = stagCustomerRepository.getByRegisterEmailOrRegisterPhoneNumber(
                registerEmail, registerPhoneNumber);

        if (enterpriseId != null) {
            registerAccounts = registerAccounts.stream().filter(ra -> ra.getEnterpriseId().equals(enterpriseId)).toList();
        }

        Validator validator = new Validator();
        validator.throwIfFalse(CollectionUtils.isNotEmpty(registerAccounts), "Không tìm thấy thông tin thành viên, vui lòng thử lại sau");

        registerAccounts.forEach(registerAccount -> {

            Enterprise registerEnterprise = enterpriseRepository.findById(Objects.requireNonNull(registerAccount).getEnterpriseId()).orElseGet(() -> null);
            Validate.isTrue(registerEnterprise != null, "Enterprise not found with id= %s", registerAccount.getEnterpriseId());

            Membership membership = new Membership();
            membership.setCustomer(customer);
            membership.setEnterprise(registerEnterprise);
            membership.setAvailablePoint(registerAccount.getInitialPoint());
            membership.setRegisterEmail(registerEmail);
            membership.setTotalBuy(0);
            membership.setRegisterPhoneNumber(registerPhoneNumber);

            membershipRepository.save(membership);
            stagCustomerRepository.delete(registerAccount);
        });

        return "Create new membership successfully";
    }

    @Override
    public void importEnterpriseRegisterCustomerIntoStagCustomer(String dataFileUrl, Enterprise enterprise) throws IOException {
        Map<Integer, StagCustomer> jsonStagCustomerMapById = parseRegisterCustomersFromFile(dataFileUrl);
        List<StagCustomer> stagCustomers = new ArrayList<>();
        jsonStagCustomerMapById.forEach((id, jsonStagCustomer) -> {
            StagCustomer stagCustomer = stagCustomerRepository.findById(id).orElseGet(() -> null);
            if (stagCustomer == null) {
                stagCustomer = new StagCustomer();
                stagCustomer.setId(id);
                stagCustomer.setEnterpriseId(enterprise.getId());
            }
            stagCustomer.setRegisterEmail(StringUtils.stripToNull(jsonStagCustomer.getRegisterEmail()));
            stagCustomer.setRegisterPhoneNumber(StringUtils.stripToNull(jsonStagCustomer.getRegisterPhoneNumber()));
            stagCustomer.setFullName(StringUtils.stripToNull(jsonStagCustomer.getFullName()));
            stagCustomer.setImportDate(LocalDateTime.now());
            stagCustomer.setInitialPoint(jsonStagCustomer.getInitialPoint());
            stagCustomers.add(stagCustomer);
        });

        stagCustomerRepository.saveAll(stagCustomers);
    }

    private Map<Integer, StagCustomer> parseRegisterCustomersFromFile(String dataFileUrl) throws IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        //TODO: insert and update
        try {
            File file = new File(dataFileUrl);
            List<String> contents = FileUtils.readLines(file, StandardCharsets.UTF_8);
            return contents.stream().map(line -> {
                try {
                    return jsonMapper.readValue(line, StagCustomer.class);
                } catch (JsonProcessingException e) {
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toMap(StagCustomer::getId, Function.identity()));
        } catch (IOException e) {
            throw new IOException(String.format("Error occur when import file %s", dataFileUrl));
        }
    }


    @Override
    public List<CustomerRegisterDto> getCustomerRegisterForEnterprise(UserAccount userAccount) {
        //TODO: fetch join here
        Enterprise enterprise = enterpriseRepository.getByUserAccountId(userAccount.getId());
        List<StagCustomer> stagCustomers = stagCustomerRepository.getByEnterpriseId(enterprise.getId());
        return customerMembershipMapper.mapToCustomerRegisterDtos(stagCustomers);
    }

    @Override
    public String importRegisterCustomers(List<CustomerRegisterDto> request, Enterprise enterprise) {
        Map<Integer, StagCustomer> existsStagCustomerMap = stagCustomerRepository.getByEnterpriseId(enterprise.getId()).stream()
                .collect(Collectors.toMap(StagCustomer::getIdChecking, Function.identity()));

        List<StagCustomer> newStagCustomers = new ArrayList<>();
        request.forEach(newCustomerRegister -> {
            StagCustomer updateStagCustomer = existsStagCustomerMap.get(newCustomerRegister.getId());
            if (updateStagCustomer != null) {
                updateStagCustomer.setInitialPoint(newCustomerRegister.getInitialPoint());
                updateStagCustomer.setFullName(newCustomerRegister.getFullName());
                updateStagCustomer.setImportDate(LocalDateTime.now());
            } else {
                StagCustomer newStagCustomer = new StagCustomer();
                newStagCustomer.setIdChecking(newCustomerRegister.getId());
                newStagCustomer.setEnterpriseId(enterprise.getId());
                newStagCustomer.setImportDate(LocalDateTime.now());
                newStagCustomer.setFullName(newCustomerRegister.getFullName());
                newStagCustomer.setRegisterEmail(newCustomerRegister.getRegisterEmail());
                newStagCustomer.setRegisterPhoneNumber(newCustomerRegister.getRegisterPhoneNumber());
                newStagCustomer.setInitialPoint(newCustomerRegister.getInitialPoint());
                newStagCustomers.add(newStagCustomer);
            }
        });

        stagCustomerRepository.saveAll(existsStagCustomerMap.values());
        stagCustomerRepository.saveAll(newStagCustomers);

        return "Import customer registers successfully";
    }
}
