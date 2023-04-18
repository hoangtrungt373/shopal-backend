package vn.group24.shopalbackend.security.auth;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.group24.shopalbackend.security.config.JwtService;
import vn.group24.shopalbackend.security.domain.ShopalToken;
import vn.group24.shopalbackend.security.domain.ShopalUser;
import vn.group24.shopalbackend.security.domain.enums.TokenType;
import vn.group24.shopalbackend.security.repository.ShopalTokenRepository;
import vn.group24.shopalbackend.security.repository.ShopalUserRepository;
import vn.group24.shopalbackend.util.Validator;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final ShopalUserRepository userRepository;
    private final ShopalTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(AuthenticationRequest request) {
        Validator validator = new Validator();
        validatePreconditionField(request, validator);
        validator.throwIfTrue(userRepository.existsByEmail(request.getEmail()), "Email already exists");
        ShopalUser user = ShopalUser.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        ShopalUser savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws BadCredentialsException {
        Validator validator = new Validator();
        ShopalUser user = userRepository.findByEmail(request.getEmail()).orElseGet(() -> null);
        validatePreconditionField(request, validator);
        validator.throwIfFalse(user != null, "Email does not exists");
        if (user != null) {
            validator.throwIfFalse(user.getRole() == request.getRole(), "Authorization failed");
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
                .build();
    }

    private void saveUserToken(ShopalUser user, String jwtToken) {
        ShopalToken token = ShopalToken.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(ShopalUser user) {
        List<ShopalToken> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }

    private Validator validatePreconditionField(AuthenticationRequest request, Validator validator) {
        validator.notNull(request.getEmail(), "Email can not be blank");
        validator.notBlank(request.getPassword(), "Password can not be blank");
        validator.isTrue(request.getRole() != null && StringUtils.isNotBlank(request.getRole().name()), "Role can not be blank");
        return validator;
    }
}
