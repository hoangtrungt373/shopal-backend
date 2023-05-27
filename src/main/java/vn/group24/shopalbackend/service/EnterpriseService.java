package vn.group24.shopalbackend.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.security.domain.UserAccount;

/**
 * @author ttg
 */
public interface EnterpriseService {

    List<EnterpriseDto> getAllEnterprise();

    EnterpriseDto getEnterpriseInfo(UserAccount userAccount);

    String updateEnterpriseInfo(EnterpriseDto enterpriseDto, MultipartFile logoUrl);
}
