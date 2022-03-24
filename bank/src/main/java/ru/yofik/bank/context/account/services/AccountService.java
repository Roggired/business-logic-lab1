package ru.yofik.bank.context.account.services;

import org.springframework.stereotype.Service;
import ru.yofik.bank.context.account.api.requests.CreateAccountRequest;
import ru.yofik.bank.context.account.api.requests.PutMoneyRequest;
import ru.yofik.bank.context.account.api.responses.CreateAccountResponse;
import ru.yofik.bank.context.account.view.AccountView;

@Service
public interface AccountService {
    CreateAccountResponse createAccount(CreateAccountRequest request);
    void putMoney(PutMoneyRequest request, String accountId);
    AccountView getAccountInfo(String accountId, int pinCode);

    boolean isPinCodeCorrect(String accountId, int pinCode);
    boolean isAccountExists(String name, String surname);
    boolean isAccountExists(String accountId);
}
