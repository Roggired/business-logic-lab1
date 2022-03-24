package ru.yofik.bank.context.account.api.responses;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateAccountResponse {
    public final int accountId;
    public final int pinCode;
}
