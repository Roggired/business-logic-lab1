package ru.yofik.bank.context.account.services;

import org.springframework.stereotype.Service;
import ru.yofik.bank.context.account.api.requests.CreateAccountRequest;
import ru.yofik.bank.context.account.api.responses.CreateAccountResponse;

@Service
public interface AccountService {
    CreateAccountResponse createAccount(CreateAccountRequest request);
}
