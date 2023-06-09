package vn.group24.shopalbackend.security.auth;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.group24.shopalbackend.controller.request.EmailDetailRequest;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.repository.CustomerRepository;
import vn.group24.shopalbackend.repository.EnterpriseRepository;
import vn.group24.shopalbackend.security.config.JwtService;
import vn.group24.shopalbackend.security.domain.UserAccount;
import vn.group24.shopalbackend.security.domain.UserAccountToken;
import vn.group24.shopalbackend.security.domain.enums.TokenType;
import vn.group24.shopalbackend.security.domain.enums.UserRole;
import vn.group24.shopalbackend.security.repository.UserAccountRepository;
import vn.group24.shopalbackend.security.repository.UserAccountTokenRepository;
import vn.group24.shopalbackend.service.EmailService;
import vn.group24.shopalbackend.util.Constants;
import vn.group24.shopalbackend.util.Validator;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserAccountRepository userRepository;
    private final CustomerRepository customerRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final UserAccountTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthenticationResponse register(RegisterRequest request) {
        Validator validator = new Validator();
        validatePreconditionField(request, validator);
        validator.throwIfTrue(userRepository.existsByEmail(request.getEmail()), "Email already exists");
        UserAccount user = UserAccount.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        UserAccount savedUser = userRepository.save(user);

        if (UserRole.CUSTOMER == request.getRole()) {
            Customer customer = new Customer();
            customer.setUserAccountId(savedUser.getId());
            customer.setContactEmail(savedUser.getUsername());
            customer.setJoinDate(LocalDate.now());
            customer.setTotalBuy(0);
            customerRepository.save(customer);
        }

        String jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userRole(request.getRole())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws BadCredentialsException {
        Validator validator = new Validator();
        UserAccount user = userRepository.findByEmail(request.getEmail()).orElseGet(() -> null);
        validatePreconditionField(request, validator);
        validator.throwIfFalse(user != null, "Email does not exists");
        if (user != null) {
            validator.throwIfFalse(user.getRole() == request.getRole(), "Authorization failed, user role and login role does not match");
            switch (request.getRole()) {
                case CUSTOMER -> {
                    boolean existsCustomer = customerRepository.existsByUserAccountId(user.getId());
                    validator.throwIfFalse(existsCustomer, "Authentication failed, can not found customer with userId = %s", user.getId().toString());
                }
                case ENTERPRISE_MANAGER -> {
                    boolean existsEnterprise = enterpriseRepository.existsByUserAccountId(user.getId());
                    validator.throwIfFalse(existsEnterprise, "Authentication failed, can not found enterprise with userId = %s", user.getId().toString());
                }
                case ADMIN -> {
                }
            }
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtService.generateToken(user);
        if (user != null) {
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
        }
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userRole(request.getRole())
                .build();
    }

    public String sendVerifyEmail(String email) {
        String otp = vn.group24.shopalbackend.util.StringUtils.generateOtp();

        EmailDetailRequest emailDetailRequest = new EmailDetailRequest();
        emailDetailRequest.setRecipient(email);
        emailDetailRequest.setSubject("Verify email register");
        emailDetailRequest.setTemplate(Constants.VERIFY_EMAIL_TEMPLATE);
        Map<String, Object> properties = new HashMap<>();
        properties.put("code", otp);
        emailDetailRequest.setProperties(properties);
        emailService.sendEmail(emailDetailRequest);
        return otp;
    }

    public boolean checkEmailExists(String email) {
        return userRepository.existsByEmail(email.replace("\"", ""));
    }

    private void saveUserToken(UserAccount user, String jwtToken) {
        UserAccountToken token = UserAccountToken.builder()
                .userAccount(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UserAccount user) {
        List<UserAccountToken> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }

    private Validator validatePreconditionField(AuthenticationRequest request, Validator validator) {
        validator.notBlank(request.getEmail(), "Email can not be blank");
        validator.notBlank(request.getPassword(), "Password can not be blank");
        validator.isTrue(request.getRole() != null && StringUtils.isNotBlank(request.getRole().name()), "Role can not be blank");
        return validator;
    }

    private Validator validatePreconditionField(RegisterRequest request, Validator validator) {
        validator.notBlank(request.getEmail(), "Email can not be blank");
        validator.notBlank(request.getPassword(), "Password can not be blank");
        validator.isTrue(request.getRole() != null && StringUtils.isNotBlank(request.getRole().name()), "Role can not be blank");
        return validator;
    }
}
