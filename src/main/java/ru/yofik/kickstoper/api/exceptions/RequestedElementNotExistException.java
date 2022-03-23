package ru.yofik.kickstoper.api.exceptions;

import ru.yofik.kickstoper.api.exceptions.ResponseCode;
import ru.yofik.kickstoper.api.exceptions.ResponseOnException;

public class RequestedElementNotExistException extends ResponseOnException {
    public RequestedElementNotExistException(String description) {
        super(ResponseCode.REQUESTED_ELEMENT_NOT_EXIST, description);
    }

    public RequestedElementNotExistException() {
        super(ResponseCode.REQUESTED_ELEMENT_NOT_EXIST, "Such element doesn't exist");
    }
}
