package ru.yofik.bank.context.account.exceptions;

import ru.yofik.bank.api.exceptions.ResponseCode;
import ru.yofik.bank.api.exceptions.ResponseOnException;

public class WrongPinCodeException extends ResponseOnException {
    public WrongPinCodeException(String description) {
        super(ResponseCode.WRONG_PIN_CODE, description);
    }

    public WrongPinCodeException() {
        super(ResponseCode.WRONG_PIN_CODE, "Pin code is wrong");
    }
}
