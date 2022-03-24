package ru.yofik.bank.context.transaction.api.requests;

import lombok.AllArgsConstructor;

import javax.validation.constraints.Positive;

@AllArgsConstructor
public class ApproveTransactionRequest {
    @Positive
    public final int pinCode;
}
