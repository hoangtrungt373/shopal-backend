package vn.group24.shopalbackend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.group24.shopalbackend.controller.response.common.CustomerDto;
import vn.group24.shopalbackend.controller.response.enterprise.CustomerMembershipDto;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.mapper.CustomerMapper;
import vn.group24.shopalbackend.mapper.CustomerMembershipMapper;
import vn.group24.shopalbackend.repository.CustomerRepository;
import vn.group24.shopalbackend.repository.EnterpriseRepository;
import vn.group24.shopalbackend.repository.StagCustomerRepository;
import vn.group24.shopalbackend.security.domain.UserAccount;
import vn.group24.shopalbackend.service.CustomerService;

/**
 * @author ttg
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private StagCustomerRepository stagCustomerRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private CustomerMembershipMapper customerMembershipMapper;

    @Override
    public CustomerDto getCustomerInfo(UserAccount userAccount) {
        Customer customer = customerRepository.getByUserAccountId(userAccount.getId());
        return customerMapper.mapToCustomerDto(customer);
    }

    @Override
    public List<CustomerMembershipDto> getCustomerMembershipForEnterprise(UserAccount userAccount) {
        //TODO: fetch join here
        Enterprise enterprise = enterpriseRepository.getByUserAccountId(userAccount.getId());
        return customerMembershipMapper.mapToCustomerMembershipDtos(enterprise);
    }

}
