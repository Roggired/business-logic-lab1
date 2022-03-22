package ru.yofik.kickstoper.context.user.exception;

import ru.yofik.kickstoper.api.exceptions.ResponseCode;
import ru.yofik.kickstoper.api.exceptions.ResponseOnException;

public class WrongRoleException extends ResponseOnException {
    public WrongRoleException() {
        super(ResponseCode.FORBIDDEN, "Forbidden");
    }
}
