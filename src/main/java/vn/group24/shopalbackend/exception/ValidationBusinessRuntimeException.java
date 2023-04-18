package vn.group24.shopalbackend.exception;

import org.apache.commons.lang3.StringUtils;

public class ValidationBusinessRuntimeException extends RuntimeException {
    private final String[] message;

    public ValidationBusinessRuntimeException(String[] message) {
        this.message = message;
    }

    public ValidationBusinessRuntimeException(Throwable cause, String[] message) {
        super(cause);
        this.message = message;
    }

    public String[] getErrorMessages() {
        return message;
    }

    @Override
    public String getMessage() {
        return message != null ? StringUtils.joinWith("\n", message) : null;
    }
}
