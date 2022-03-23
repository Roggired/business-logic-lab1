package ru.yofik.bank.context.account.services;

import org.springframework.stereotype.Component;
import ru.yofik.bank.context.account.model.Account;

import java.util.UUID;

@Component
public class AccountFactoryImpl implements AccountFactory {
    private final static int DEFAULT_PIN_CODE = 1111;
    private final static int DEFAULT_BALANCE = 0;

    @Override
    public Account create(String name, String surname) {
        String accountId = UUID.randomUUID().toString();

        return new Account(
                0,
                accountId,
                name,
                surname,
                generatePinCode(),
                DEFAULT_BALANCE
        );
    }

    private int generatePinCode() {
        return DEFAULT_PIN_CODE;
    }
}
