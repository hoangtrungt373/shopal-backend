package vn.group24.shopalbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.group24.shopalbackend.controller.request.CatalogSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.common.CatalogDto;
import vn.group24.shopalbackend.service.CatalogService;

/**
 *
 * @author ttg
 */
@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/all")
    public ResponseEntity<List<CatalogDto>> getAllCatalog() {
        return ResponseEntity.ok(catalogService.getAllCatalog());
    }

    @PostMapping("/get-by-criteria")
    public ResponseEntity<List<CatalogDto>> getByCriteria(@RequestBody CatalogSearchCriteriaRequest criteria) {
        return ResponseEntity.ok(catalogService.getByCriteria(criteria));
    }
}
