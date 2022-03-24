package ru.yofik.bank.context.account.view;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountView {
    private final String name;
    private final String surname;
    private final int balance;
}
