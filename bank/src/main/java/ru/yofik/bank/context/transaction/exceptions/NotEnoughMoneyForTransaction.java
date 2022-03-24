package ru.yofik.bank.context.transaction.exceptions;

import ru.yofik.bank.api.exceptions.ResponseCode;
import ru.yofik.bank.api.exceptions.ResponseOnException;

public class NotEnoughMoneyForTransaction extends ResponseOnException {
    public NotEnoughMoneyForTransaction(String description) {
        super(ResponseCode.NOT_ENOUGH_MONEY_FOT_TRANSACTION, description);
    }
}
