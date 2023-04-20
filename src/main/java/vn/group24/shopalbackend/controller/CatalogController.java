package vn.group24.shopalbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ttg
 */
@RestController
@RequestMapping("/api/catalog")
public class CatalogController {


    @GetMapping("/all")
    public ResponseEntity<String> getAllCatalog() {
        return ResponseEntity.ok("Catalog here");
    }
}
