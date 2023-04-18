package vn.group24.shopalbackend.controller.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

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

