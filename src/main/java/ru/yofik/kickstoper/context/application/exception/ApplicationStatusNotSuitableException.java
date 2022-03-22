package ru.yofik.kickstoper.context.application.exception;

import ru.yofik.kickstoper.api.exceptions.ResponseCode;
import ru.yofik.kickstoper.api.exceptions.ResponseOnException;

public class ApplicationStatusNotSuitableException extends ResponseOnException {

    public ApplicationStatusNotSuitableException() {
        super(ResponseCode.APPLICATION_STATUS_NOT_SUITABLE, "Application status is not suitavle in this case");
    }
}
