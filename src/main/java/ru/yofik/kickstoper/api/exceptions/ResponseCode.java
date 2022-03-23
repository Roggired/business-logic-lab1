package ru.yofik.kickstoper.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {
    BAD_REQUEST(4000),
    PROJECT_NAME_IS_NOT_FREE(4001),
    PROJECT_END_DATE_IS_BEFORE_NOW(4002),
    REQUESTED_ELEMENT_NOT_EXIST(4004),
    APPLICATION_STATUS_NOT_SUITABLE(4005),
    ELEMENT_ALREADY_EXISTS(4006),
    INVALID_AMOUNT_OF_MONEY(4007),
    NO_SUCH_ACCOUNT(4008),
    UNAUTHENTICATED(4010),
    FORBIDDEN(4030),
    INTERNAL_ERROR(500);

    private final int code;
}
