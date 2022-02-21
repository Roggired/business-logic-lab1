package ru.yofik.kickstoper.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseOnException extends RuntimeException {
    private final ResponseCode code;
    private final String description;
}
