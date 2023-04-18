package vn.group24.shopalbackend.controller.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ttg
 */
@Getter
@Setter
public class RestError {

    String errorCode;
    String errorMessage;

    public RestError(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}

