package vn.group24.shopalbackend.service;

import java.util.List;

import vn.group24.shopalbackend.controller.request.EnterpriseRegisterRequest;
import vn.group24.shopalbackend.controller.response.admin.EnterpriseCooperationRequestDto;

/**
 *
 * @author ttg
 */
public interface EnterpriseCooperationRequestService {

    String handleReceiveEnterpriseCooperationRequest(EnterpriseRegisterRequest request);

    List<EnterpriseCooperationRequestDto> getAllEnterpriseCooperationRequest();

    String handleAcceptEnterpriseCooperationRequest(Integer requestId);
}
