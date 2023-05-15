package vn.group24.shopalbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.group24.shopalbackend.controller.request.EnterpriseRegisterRequest;
import vn.group24.shopalbackend.controller.response.admin.EnterpriseCooperationRequestDto;
import vn.group24.shopalbackend.service.EnterpriseCooperationRequestService;

/**
 *
 * @author ttg
 */
@RestController
@RequestMapping("/api/cooperation-request")
public class CooperationRequestController extends AbstractController {

    @Autowired
    private EnterpriseCooperationRequestService enterpriseCooperationRequestService;

    @PostMapping("/receive")
    public ResponseEntity<String> handleReceiveEnterpriseCooperationRequest(@RequestBody EnterpriseRegisterRequest request) {
        return ResponseEntity.ok(enterpriseCooperationRequestService.handleReceiveEnterpriseCooperationRequest(request));
    }

    @PostMapping("/all")
    public ResponseEntity<List<EnterpriseCooperationRequestDto>> getAllEnterpriseCooperationRequest() {
        return ResponseEntity.ok(enterpriseCooperationRequestService.getAllEnterpriseCooperationRequest());
    }

    @PostMapping("/accept")
    public ResponseEntity<String> handleAcceptEnterpriseCooperationRequest(@RequestBody Integer requestId) {
        return ResponseEntity.ok(enterpriseCooperationRequestService.handleAcceptEnterpriseCooperationRequest(requestId));
    }

}
