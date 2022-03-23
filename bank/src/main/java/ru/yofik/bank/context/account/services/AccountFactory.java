package ru.yofik.bank.context.account.services;

import org.springframework.stereotype.Component;
import ru.yofik.bank.context.account.model.Account;

import javax.validation.constraints.NotNull;

@Component
public interface AccountFactory {
    @NotNull Account create(String name, String surname);
}
