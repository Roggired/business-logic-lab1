package ru.yofik.kickstoper.context.user.exception;

import ru.yofik.kickstoper.api.exceptions.ResponseCode;
import ru.yofik.kickstoper.api.exceptions.ResponseOnException;

public class UserNotExistsException extends ResponseOnException {
    public UserNotExistsException() {
        super(ResponseCode.REQUESTED_ELEMENT_NOT_EXIST, "User does not exist");
    }
}
