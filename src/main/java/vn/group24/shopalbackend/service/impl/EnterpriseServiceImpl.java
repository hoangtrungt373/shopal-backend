package vn.group24.shopalbackend.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vn.group24.shopalbackend.controller.request.EnterpriseSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.mapper.EnterpriseMapper;
import vn.group24.shopalbackend.repository.EnterpriseRepository;
import vn.group24.shopalbackend.security.domain.UserAccount;
import vn.group24.shopalbackend.service.EnterpriseService;
import vn.group24.shopalbackend.util.Constants;
import vn.group24.shopalbackend.util.FileUtils;

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
    public List<EnterpriseDto> getEnterpriseByCriteria(EnterpriseSearchCriteriaRequest criteria) {
        return enterpriseRepository.getByCriteria(criteria).stream().map(enterpriseMapper::mapToEnterpriseDto).collect(Collectors.toList());
    }

    @Override
    public EnterpriseDto getEnterpriseInfo(UserAccount userAccount) {
        Enterprise enterprise = enterpriseRepository.getByUserAccountId(userAccount.getId());
        return enterpriseMapper.mapToEnterpriseDto(enterprise);
    }

    @Override
    public String updateEnterpriseInfo(EnterpriseDto enterpriseDto, MultipartFile logoUrl) {
        Enterprise enterprise = enterpriseRepository.findById(enterpriseDto.getId()).orElseGet(() -> null);
        Validate.isTrue(enterprise != null, "Can not found Enterprise with id = %s", enterpriseDto.getId());
        enterprise.setEnterpriseName(enterpriseDto.getEnterpriseName());
        enterprise.setPhoneNumber(enterprise.getPhoneNumber());
        enterprise.setAddress(enterpriseDto.getAddress());
        enterprise.setWebsiteUrl(enterpriseDto.getWebsiteUrl());
        enterprise.setTaxId(enterpriseDto.getTaxId());
        if (logoUrl != null) {
            try {
                enterprise.setLogoUrl(FileUtils.saveFileWithRandomName(logoUrl, Constants.ENTERPRISE_LOGO_DIRECTORY));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        enterpriseRepository.save(enterprise);

        return "Update enterprise profile successfully";
    }
}
