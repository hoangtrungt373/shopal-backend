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

    @GetMapping("/main/all")
    public ResponseEntity<List<CatalogDto>> getAllMainCatalog() {
        return ResponseEntity.ok(catalogService.getAllMainCatalog());
    }

    @PostMapping("/get-by-criteria")
    public ResponseEntity<List<CatalogDto>> getCatalogByCriteria(@RequestBody CatalogSearchCriteriaRequest criteria) {
        return ResponseEntity.ok(catalogService.getByCriteria(criteria));
    }

    @GetMapping("/child/all")
    public ResponseEntity<List<CatalogDto>> getAllChildCatalog() {
        return ResponseEntity.ok(catalogService.getAllChildCatalog());
    }

    @GetMapping("/main/detail/all")
    public ResponseEntity<List<CatalogDto>> getAllMainCatalogWithDetail() {
        return ResponseEntity.ok(catalogService.getAllMainCatalogWithDetail());
    }

    @GetMapping("/child/detail/all")
    public ResponseEntity<List<CatalogDto>> getAllChildCatalogWithDetail() {
        return ResponseEntity.ok(catalogService.getAllChildCatalogWithDetail());
    }
}
