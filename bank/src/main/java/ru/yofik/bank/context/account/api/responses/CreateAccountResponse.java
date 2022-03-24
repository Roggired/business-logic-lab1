package ru.yofik.bank.context.account.api.responses;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateAccountResponse {
    public final String accountId;
    public final int pinCode;
}
