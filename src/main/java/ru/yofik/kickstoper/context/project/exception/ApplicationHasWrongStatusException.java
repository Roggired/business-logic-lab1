package ru.yofik.kickstoper.context.project.exception;

import ru.yofik.kickstoper.api.exceptions.ResponseCode;
import ru.yofik.kickstoper.api.exceptions.ResponseOnException;

public class ApplicationHasWrongStatusException extends ResponseOnException {
    public ApplicationHasWrongStatusException(String description) {
        super(ResponseCode.APPLICATION_STATUS_NOT_SUITABLE, description);
    }
}
