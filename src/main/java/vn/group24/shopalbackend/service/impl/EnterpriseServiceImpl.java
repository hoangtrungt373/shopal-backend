package vn.group24.shopalbackend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.mapper.EnterpriseMapper;
import vn.group24.shopalbackend.repository.EnterpriseRepository;
import vn.group24.shopalbackend.security.domain.UserAccount;
import vn.group24.shopalbackend.service.EnterpriseService;

/**
 * @author ttg
 */
@Service
@Transactional
public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Override
    public List<EnterpriseDto> getAllEnterprise() {
        return enterpriseMapper.mapToEnterpriseDtos(enterpriseRepository.findAll());
    }

    @Override
    public EnterpriseDto getEnterpriseInfo(UserAccount userAccount) {
        Enterprise enterprise = enterpriseRepository.getByUserAccountId(userAccount.getId());
        return enterpriseMapper.mapToEnterpriseDto(enterprise);
    }
}
