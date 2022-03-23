package ru.yofik.bank.context.account.api.requests;

import lombok.AllArgsConstructor;

import javax.validation.constraints.Positive;

@AllArgsConstructor
public class PutMoneyRequest {
    @Positive
    public final int amount;

    @Positive
    public final int pinCode;
}
