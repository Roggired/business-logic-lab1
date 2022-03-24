package ru.yofik.bank.api.exceptions;

public class RequestedElementAlreadyExistsException extends ResponseOnException{
    public RequestedElementAlreadyExistsException(String description) {
        super(ResponseCode.ELEMENT_ALREADY_EXISTS, description);
    }

    public RequestedElementAlreadyExistsException() {
        super(ResponseCode.REQUESTED_ELEMENT_NOT_EXIST, "Such element doesn't exist");
    }
}
