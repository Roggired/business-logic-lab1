package ru.yofik.bank.context.transaction.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yofik.bank.context.transaction.model.Transaction;

@Data
@AllArgsConstructor
public class TransactionView {
    private final String fromAccount;
    private final String toAccount;
    private final int amount;
}
