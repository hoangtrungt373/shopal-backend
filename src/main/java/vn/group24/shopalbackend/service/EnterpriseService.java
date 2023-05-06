package vn.group24.shopalbackend.service;

import java.util.List;

import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.security.domain.UserAccount;

/**
 * @author ttg
 */
public interface EnterpriseService {

    List<EnterpriseDto> getAllEnterprise();

    EnterpriseDto getEnterpriseInfo(UserAccount userAccount);
}
