package vn.group24.shopalbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.group24.shopalbackend.controller.request.CooperationContractSearchCriteriaRequest;
import vn.group24.shopalbackend.domain.dto.CreateOrUpdateContractRequestAnn;
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

    @PostMapping("/get-by-criteria")
//    @PreAuthorize("hasRole('ENTERPRISE_MANAGER')")
    public ResponseEntity<List<CooperationContractDto>> getCooperationContractByCriteria(@RequestBody CooperationContractSearchCriteriaRequest criteria) {
        List<CooperationContractDto> cooperationContractDtos = cooperationContractService.getCooperationContractByCriteria(criteria);
        return ResponseEntity.ok().body(cooperationContractDtos);
    }

    @PostMapping("/receive-create-or-update-contract-request")
//    @PreAuthorize("hasRole('ENTERPRISE_MANAGER')")
    // TODO: change to request
    public ResponseEntity<String> handleReceiveCreateOrUpdateContract(@RequestBody CooperationContractDto request) {
        return ResponseEntity.ok().body(cooperationContractService.handleReceiveCreateOrUpdateContract(request));
    }

    @PostMapping("/create-or-update-contract-request/get-by-criteria")
//    @PreAuthorize("hasRole('ENTERPRISE_MANAGER')")
    // TODO: change to request
    public ResponseEntity<List<CreateOrUpdateContractRequestAnn>> getAllCreateOrUpdateContractAnn() {
        return ResponseEntity.ok().body(cooperationContractService.getAllCreateOrUpdateContractAnn());
    }

    @PostMapping("/create-or-update-contract-request/accept")
//    @PreAuthorize("hasRole('ENTERPRISE_MANAGER')")
    // TODO: change to request
    public ResponseEntity<String> handleAcceptCreateOrUpdateContractRequest(@RequestBody CreateOrUpdateContractRequestAnn request) {
        return ResponseEntity.ok().body(cooperationContractService.handleAcceptCreateOrUpdateContractRequest(request));
    }
}
