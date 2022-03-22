package ru.yofik.kickstoper.context.user.exception;

import ru.yofik.kickstoper.api.exceptions.ResponseCode;
import ru.yofik.kickstoper.api.exceptions.ResponseOnException;

public class UserAlreadyExistsException extends ResponseOnException {
    public UserAlreadyExistsException() {
        super(ResponseCode.ELEMENT_ALREADY_EXISTS, "User already exists");
    }
}
