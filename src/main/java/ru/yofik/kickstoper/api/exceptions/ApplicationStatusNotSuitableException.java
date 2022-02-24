package ru.yofik.kickstoper.api.exceptions;

public class ApplicationStatusNotSuitableException extends ResponseOnException {

    public ApplicationStatusNotSuitableException() {
        super(ResponseCode.APPLICATION_STATUS_NOT_SUITABLE, "Application status is not suitavle in this case");
    }
}
