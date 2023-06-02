package vn.group24.shopalbackend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import vn.group24.shopalbackend.controller.request.EnterpriseRegisterRequest;
import vn.group24.shopalbackend.controller.request.EnterpriseSearchCriteriaRequest;
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

    @PostMapping("/get-by-criteria")
    public ResponseEntity<List<EnterpriseDto>> getEnterpriseByCriteria(@RequestBody EnterpriseSearchCriteriaRequest criteria) {
        return ResponseEntity.ok(enterpriseService.getEnterpriseByCriteria(criteria));
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
    public ResponseEntity<String> handleAcceptEnterpriseCooperationRequest(@RequestBody EnterpriseRegisterRequestAnn request) {
        return ResponseEntity.ok(enterpriseRegisterRequestService.handleAcceptEnterpriseCooperationRequest(request));
    }

    @PostMapping(value = "/update", produces = {MediaType.ALL_VALUE, "application/json"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> updateCurrentCustomerInfo(@RequestPart(name = "dto") EnterpriseDto request, @RequestPart(name = "uploadLogoUrl", required = false) MultipartFile logoUrl) throws IOException {
        return ResponseEntity.ok().body(enterpriseService.updateEnterpriseInfo(request, logoUrl));
    }
}
