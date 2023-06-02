package vn.group24.shopalbackend.service;

import java.util.List;

import vn.group24.shopalbackend.controller.CooperationContractDto;
import vn.group24.shopalbackend.controller.request.CooperationContractSearchCriteriaRequest;
import vn.group24.shopalbackend.domain.dto.CreateOrUpdateContractRequestAnn;

/**
 * @author ttg
 */
public interface CooperationContractService {

    List<CooperationContractDto> getCooperationContractByCriteria(CooperationContractSearchCriteriaRequest criteria);

    String handleReceiveCreateOrUpdateContract(CooperationContractDto request);

    List<CreateOrUpdateContractRequestAnn> getAllCreateOrUpdateContractAnn();

    String handleAcceptCreateOrUpdateContractRequest(CreateOrUpdateContractRequestAnn createOrUpdateContractRequestAnn);

    String cancelCreateOrUpdateContractAnn(Integer annId);

    String syncContractStatus();
}
