package vn.group24.shopalbackend.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.group24.shopalbackend.controller.AbstractController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController extends AbstractController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> handleUserRegister(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> handleUserAuthenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/send-verify-email")
    public ResponseEntity<String> sendVerifyEmail(@RequestBody String email) {
        return ResponseEntity.ok(authenticationService.sendVerifyEmail(email));
    }

    @PostMapping("/check-email-exists")
    public ResponseEntity<Boolean> checkEmailExists(@RequestBody String email) {
        return ResponseEntity.ok(authenticationService.checkEmailExists(email));
    }
}
