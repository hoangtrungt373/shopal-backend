package vn.group24.shopalbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.group24.shopalbackend.controller.request.EnterpriseCooperationContractSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.enterprise.EnterpriseCooperationContractDto;
import vn.group24.shopalbackend.service.CooperationContractService;

/**
 *
 * @author ttg
 */

@RestController
@RequestMapping("/api/contract")
public class ContractController extends AbstractController {

    @Autowired
    private CooperationContractService cooperationContractService;

    @PostMapping("/current-enterprise/get-by-criteria")
//    @PreAuthorize("hasRole('ENTERPRISE_MANAGER')")
    public ResponseEntity<List<EnterpriseCooperationContractDto>> getCooperationContractForCurrentEnterpriseByCriteria(@RequestBody EnterpriseCooperationContractSearchCriteriaRequest criteria) {
        List<EnterpriseCooperationContractDto> enterpriseCooperationContractDtos = cooperationContractService.getCooperationContractForEnterpriseByCriteria(userUtils.getAuthenticateEnterprise(), criteria);
        return ResponseEntity.ok().body(enterpriseCooperationContractDtos);
    }
}
