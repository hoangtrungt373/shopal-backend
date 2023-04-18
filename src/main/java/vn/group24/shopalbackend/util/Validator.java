package vn.group24.shopalbackend.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import vn.group24.shopalbackend.exception.ValidationBusinessRuntimeException;


public class Validator {

    private final List<String> messages = new ArrayList<>();

    public Validator() {
    }

    public boolean isTrue(boolean condition, String errorMessage, String... errorMessageParamValues) {
        if (condition) {
            return true;
        }
        Object[] objectsParams = Arrays.stream(errorMessageParamValues).toArray();
        String message = String.format(errorMessage, objectsParams);
        checkAndAddUniqueMessage(message);
        return false;
    }

    public void checkAndAddUniqueMessage(String message) {
        if (messages.stream().noneMatch(existsMessage -> existsMessage.equals(message))) {
            messages.add(message);
        }
    }

    public void notBlank(String value, String errorMessage, String... errorMessageParamValues) {
        isTrue(StringUtils.isNotBlank(value), errorMessage, errorMessageParamValues);
    }

    public boolean notNull(Object value, String errorMessage, String... errorMessageParamValues) {
        return isTrue(value != null, errorMessage, errorMessageParamValues);
    }

    public boolean isNull(Object value, String errorMessage, String... errorMessageParamValues) {
        return isTrue(value == null, errorMessage, errorMessageParamValues);
    }

    public void throwIfFalse(boolean valid, String errorMessage, String... errorMessageParamValues) {
        if (isTrue(valid, errorMessage, errorMessageParamValues)) {
            return;
        }

        throwAllErrorMessages();
    }

    public void throwIfTrue(boolean valid, String errorMessage, String... errorMessageParamValues) {
        if (isTrue(!valid, errorMessage, errorMessageParamValues)) {
            return;
        }

        throwAllErrorMessages();
    }

    public void throwAllErrorMessages() {
        if (CollectionUtils.isEmpty(messages)) {
            return;
        }

        String[] messageArray = new String[messages.size()];
        messageArray = messages.toArray(messageArray);
        messages.clear();
        throw new ValidationBusinessRuntimeException(messageArray);
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }
}