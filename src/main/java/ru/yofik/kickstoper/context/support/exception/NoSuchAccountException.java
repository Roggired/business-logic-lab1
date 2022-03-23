package ru.yofik.kickstoper.context.support.exception;

import ru.yofik.kickstoper.api.exceptions.ResponseCode;
import ru.yofik.kickstoper.api.exceptions.ResponseOnException;

public class NoSuchAccountException extends ResponseOnException {
    public NoSuchAccountException() {
        super(ResponseCode.NO_SUCH_ACCOUNT, "Invalid account id");
    }
}
