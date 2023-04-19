package vn.group24.shopalbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.group24.shopalbackend.domain.enums.Language;
import vn.group24.shopalbackend.service.LanguageService;

/**
 * @author ttg
 */
@RestController
@RequestMapping("/language")
public class SysLanguageController {

    @Autowired
    private LanguageService languageService;

    @GetMapping("/change-language/{reqLan}")
    public ResponseEntity<String> changeCurrentLanguage(@PathVariable Language reqLan) {
        languageService.changeCurrentLanguage(reqLan);
        return ResponseEntity.ok("Catalog here");
    }
}
