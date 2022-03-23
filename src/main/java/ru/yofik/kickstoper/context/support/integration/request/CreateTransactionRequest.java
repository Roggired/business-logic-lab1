package ru.yofik.kickstoper.context.support.integration.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateTransactionRequest {
    public final String fromAccount;
    public final String toAccount;
    public final int amount;
}
