package ru.yofik.bank.context.account.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yofik.bank.context.account.model.Account;

@Data
@AllArgsConstructor
public class AccountView {
    private final String name;
    private final String surname;
    private final int balance;

    public static AccountView from(Account account) {
        return new AccountView(
                account.getName(),
                account.getSurname(),
                account.getBalance()
        );
    }
}
