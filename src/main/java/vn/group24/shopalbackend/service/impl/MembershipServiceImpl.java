package vn.group24.shopalbackend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.group24.shopalbackend.controller.response.customer.EnterpriseMembershipDto;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Membership;
import vn.group24.shopalbackend.mapper.EnterpriseMembershipMapper;
import vn.group24.shopalbackend.repository.MembershipRepository;
import vn.group24.shopalbackend.service.MembershipService;

/**
 *
 * @author ttg
 */
@Service
@Transactional
public class MembershipServiceImpl implements MembershipService {

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private EnterpriseMembershipMapper enterpriseMembershipMapper;

    @Override
    public List<EnterpriseMembershipDto> getEnterpriseMembershipForCustomer(Customer customer) {
        List<Membership> memberships = membershipRepository.getByCustomerId(customer.getId());
        return enterpriseMembershipMapper.mapToEnterpriseMembershipDto(memberships);
    }
}
