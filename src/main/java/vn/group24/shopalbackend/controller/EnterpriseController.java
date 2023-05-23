package vn.group24.shopalbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import vn.group24.shopalbackend.controller.request.EnterpriseRegisterRequest;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.domain.dto.EnterpriseRegisterRequestAnn;
import vn.group24.shopalbackend.security.domain.UserAccount;
import vn.group24.shopalbackend.service.EnterpriseRegisterRequestService;
import vn.group24.shopalbackend.service.EnterpriseService;

/**
 *
 * @author ttg
 */
@RestController
@RequestMapping("/api/enterprise")
public class EnterpriseController extends AbstractController {

    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private EnterpriseRegisterRequestService enterpriseRegisterRequestService;


    @GetMapping("/all")
    public ResponseEntity<List<EnterpriseDto>> getAllEnterprise() {
        return ResponseEntity.ok(enterpriseService.getAllEnterprise());
    }


    @GetMapping("/current-enterprise/info")
    public ResponseEntity<EnterpriseDto> getCurrentEnterpriseInfo() {
        UserAccount userAccount = userUtils.getAuthenticateUser();
        return ResponseEntity.ok(enterpriseService.getEnterpriseInfo(userAccount));
    }

    @PostMapping("/register/receive")
    public ResponseEntity<String> handleReceiveEnterpriseRegisterRequest(@RequestBody EnterpriseRegisterRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(enterpriseRegisterRequestService.handleReceiveEnterpriseRegisterRequest(request));
    }

    @PostMapping("/register/get-all")
    public ResponseEntity<List<EnterpriseRegisterRequestAnn>> getAllEnterpriseRegisterRequest() {
        return ResponseEntity.ok(enterpriseRegisterRequestService.getAllEnterpriseRegisterRequest());
    }

    @PostMapping("/register/accept")
    public ResponseEntity<String> handleAcceptEnterpriseCooperationRequest(@RequestBody Integer requestId) {
        return ResponseEntity.ok(enterpriseRegisterRequestService.handleAcceptEnterpriseCooperationRequest(requestId));
    }
}
