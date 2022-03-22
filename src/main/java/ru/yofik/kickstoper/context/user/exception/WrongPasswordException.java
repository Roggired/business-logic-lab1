package ru.yofik.kickstoper.context.user.exception;

import ru.yofik.kickstoper.api.exceptions.ResponseCode;
import ru.yofik.kickstoper.api.exceptions.ResponseOnException;

public class WrongPasswordException extends ResponseOnException {
    public WrongPasswordException() {
        super(ResponseCode.UNAUTHENTICATED, "Unauthenticated");
    }
}
