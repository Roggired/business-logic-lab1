package ru.yofik.kickstoper.context.support.integration.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAccountInfoRequest {
    public final String accountId;
    public final int pinCode;
}
