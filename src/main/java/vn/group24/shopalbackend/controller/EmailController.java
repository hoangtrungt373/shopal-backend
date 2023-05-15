package vn.group24.shopalbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.group24.shopalbackend.controller.request.EmailDetailRequest;
import vn.group24.shopalbackend.service.EmailService;

/**
 *
 * @author ttg
 */
@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/verify-account/send")
    public ResponseEntity<String> sendMail(@RequestBody EmailDetailRequest details) {
        String status = emailService.sendEmail(details);
        return ResponseEntity.ok().body(status);
    }
}
