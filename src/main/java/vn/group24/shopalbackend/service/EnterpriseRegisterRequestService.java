package vn.group24.shopalbackend.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import vn.group24.shopalbackend.controller.request.EnterpriseRegisterRequest;
import vn.group24.shopalbackend.domain.dto.EnterpriseRegisterRequestAnn;

/**
 *
 * @author ttg
 */
public interface EnterpriseRegisterRequestService {

    String handleReceiveEnterpriseRegisterRequest(EnterpriseRegisterRequest request) throws JsonProcessingException;

    List<EnterpriseRegisterRequestAnn> getAllEnterpriseRegisterRequest();

    String handleAcceptEnterpriseCooperationRequest(Integer requestId);
}
