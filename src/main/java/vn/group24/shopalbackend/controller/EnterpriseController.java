package vn.group24.shopalbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.security.domain.UserAccount;
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


    @GetMapping("/all")
    public ResponseEntity<List<EnterpriseDto>> getAllEnterprise() {
        return ResponseEntity.ok(enterpriseService.getAllEnterprise());
    }


    @GetMapping("/current-enterprise/info")
    public ResponseEntity<EnterpriseDto> getCurrentEnterpriseInfo() {
        UserAccount userAccount = userUtils.getAuthenticateUser();
        return ResponseEntity.ok(enterpriseService.getEnterpriseInfo(userAccount));
    }
}
