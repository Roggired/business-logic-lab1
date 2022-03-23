package ru.yofik.bank.api.exceptions;

public class InternalServerException extends ResponseOnException {
    public InternalServerException() {
        super(ResponseCode.INTERNAL_ERROR, "Internal error, please, contact our support team (we know that we do not have such team, but you do not ^_^ )");
    }
}
