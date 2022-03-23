package ru.yofik.bank.context.account.api.responses;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetAccountResponse {
    public final int balance;
    public final String name;
    public final String surname;
}
