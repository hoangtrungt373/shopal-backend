package vn.group24.shopalbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import vn.group24.shopalbackend.controller.request.EnterpriseRegisterRequest;
import vn.group24.shopalbackend.controller.response.admin.EnterpriseRegisterRequestAnnDto;
import vn.group24.shopalbackend.service.EnterpriseRegisterRequestService;

/**
 *
 * @author ttg
 */
@RestController
@RequestMapping("/api/cooperation-request")
public class CooperationRequestController extends AbstractController {

    @Autowired
    private EnterpriseRegisterRequestService enterpriseRegisterRequestService;

    @PostMapping("/receive")
    public ResponseEntity<String> handleReceiveEnterpriseRegisterRequest(@RequestBody EnterpriseRegisterRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(enterpriseRegisterRequestService.handleReceiveEnterpriseRegisterRequest(request));
    }

    @PostMapping("/all")
    public ResponseEntity<List<EnterpriseRegisterRequestAnnDto>> getAllEnterpriseRegisterRequest() {
        return ResponseEntity.ok(enterpriseRegisterRequestService.getAllEnterpriseRegisterRequest());
    }

    @PostMapping("/accept")
    public ResponseEntity<String> handleAcceptEnterpriseCooperationRequest(@RequestBody Integer requestId) {
        return ResponseEntity.ok(enterpriseRegisterRequestService.handleAcceptEnterpriseCooperationRequest(requestId));
    }

}
