package ru.yofik.bank.context.account.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yofik.bank.api.exceptions.RequestedElementAlreadyExistsException;
import ru.yofik.bank.context.account.api.requests.CreateAccountRequest;
import ru.yofik.bank.context.account.api.responses.CreateAccountResponse;
import ru.yofik.bank.context.account.model.Account;
import ru.yofik.bank.context.account.repository.AccountRepository;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest request) {
        if (isAccountExists(request.name, request.surname)) {
            log.warn(() -> "Account for " + request.name + " " + request.surname + " already exists");
            throw new RequestedElementAlreadyExistsException("Account already exists");
        }

        Account account = createAccountFromRequest(request);
        int newAccountId = accountRepository.saveAndFlush(account).getId();

        log.info(() -> "An account with id: " + newAccountId + " has been created");
        return new CreateAccountResponse(
                newAccountId,
                account.getPinCode()
        );
    }

    private Account createAccountFromRequest(CreateAccountRequest request) {
        return new Account(
                0,
                request.name,
                request.surname,
                1111,
                0
        );
    }

    private boolean isAccountExists(String name, String surname) {
        return accountRepository.findByNameAndSurname(name, surname) == null;
    }
}
