package vn.group24.shopalbackend.service;

import java.util.List;

import vn.group24.shopalbackend.controller.response.enterprise.EnterpriseAccountingDto;
import vn.group24.shopalbackend.domain.Enterprise;

/**
 * @author ttg
 */
public interface AccountingService {

    List<EnterpriseAccountingDto> getAllAccountingForEnterprise(Enterprise enterprise);
}
