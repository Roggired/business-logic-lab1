package ru.yofik.bank.context.transaction.api.requests;

import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@AllArgsConstructor
public class CreateTransactionRequest {
    @NotBlank
    public final String fromAccount;

    @NotBlank
    public final String toAccount;

    @Positive
    public final int amount;
}
