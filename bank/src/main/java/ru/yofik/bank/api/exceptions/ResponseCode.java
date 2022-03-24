package ru.yofik.bank.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {
    BAD_REQUEST(4000),
    WRONG_PIN_CODE(4001),
    REQUESTED_ELEMENT_NOT_EXIST(4004),
    ELEMENT_ALREADY_EXISTS(4006),
    UNAUTHENTICATED(4010),
    FORBIDDEN(4030),
    INTERNAL_ERROR(500);

    private final int code;
}
