package vn.group24.shopalbackend.controller.response.common;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ttg
 */
@Getter
@Setter
public class RestError {
    Integer status;
    String errorCode;
    String errorMessage;

    public RestError(HttpStatus status, String errorCode, String errorMessage) {
        super();
        this.status = status.value();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}

