package ru.yofik.kickstoper.api.exceptions;

public class RequestedElementNotExistException extends ResponseOnException {
    public RequestedElementNotExistException(String description) {
        super(ResponseCode.REQUESTED_ELEMENT_NOT_EXIST, description);
    }

    public RequestedElementNotExistException() {
        super(ResponseCode.REQUESTED_ELEMENT_NOT_EXIST, "Such element doesn't exist");
    }
}
