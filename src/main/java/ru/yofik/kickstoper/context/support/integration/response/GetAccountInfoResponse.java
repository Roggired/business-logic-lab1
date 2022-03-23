package ru.yofik.kickstoper.context.support.integration.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAccountInfoResponse {
    public final int balance;
    public final String name;
    public final String surname;
}
