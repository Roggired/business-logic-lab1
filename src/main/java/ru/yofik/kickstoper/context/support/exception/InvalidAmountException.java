package ru.yofik.kickstoper.context.support.exception;

import ru.yofik.kickstoper.api.exceptions.ResponseCode;
import ru.yofik.kickstoper.api.exceptions.ResponseOnException;

public class InvalidAmountException extends ResponseOnException {
    public InvalidAmountException() {
        super(ResponseCode.INVALID_AMOUNT_OF_MONEY, "Amount of money should be greater than 0");
    }
}
