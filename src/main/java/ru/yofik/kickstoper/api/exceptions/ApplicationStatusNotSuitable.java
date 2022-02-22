package ru.yofik.kickstoper.api.exceptions;

public class ApplicationStatusNotSuitable extends ResponseOnException {

    public ApplicationStatusNotSuitable() {
        super(ResponseCode.APPLICATION_STATUS_NOT_SUITABLE, "Application status is not suitavle in this case");
    }
}
