package vn.group24.shopalbackend.service;

import java.util.List;

import vn.group24.shopalbackend.controller.request.EnterpriseCooperationContractSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.enterprise.EnterpriseCooperationContractDto;
import vn.group24.shopalbackend.domain.Enterprise;

/**
 * @author ttg
 */
public interface CooperationContractService {

    List<EnterpriseCooperationContractDto> getCooperationContractForEnterpriseByCriteria(Enterprise enterprise, EnterpriseCooperationContractSearchCriteriaRequest criteria);
}
