package vn.group24.shopalbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import vn.group24.shopalbackend.controller.response.RestError;
import vn.group24.shopalbackend.exception.ValidationBusinessRuntimeException;

/**
 * @author ttg
 */
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class})
    @ResponseBody
    public ResponseEntity<RestError> handleBadCredentialsException(BadCredentialsException ex) {

        RestError re = new RestError(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.toString(),
                "Email or password not correct");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public ResponseEntity<RestError> handleAuthenticationException(Exception ex) {

        RestError re = new RestError(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.toString(),
                "Full authentication is required to access this resource");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }

    @ExceptionHandler({ValidationBusinessRuntimeException.class})
    @ResponseBody
    public ResponseEntity<RestError> handleValidationBusinessRuntimeException(ValidationBusinessRuntimeException ex) {

        RestError re = new RestError(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(),
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re);
    }

}
