package ru.yofik.kickstoper.context.support.integration.factory;

import org.springframework.stereotype.Component;
import ru.yofik.kickstoper.context.support.integration.response.GetAccountInfoResponse;
import ru.yofik.kickstoper.context.support.model.Account;

@Component
public class AccountFactory {
    public Account createAccount(String accountId, GetAccountInfoResponse response) {
        Account account = new Account();
        account.setId(accountId);
        account.setName(response.name);
        account.setSurname(response.surname);
        account.setBalance(response.balance);
        return account;
    }
}
