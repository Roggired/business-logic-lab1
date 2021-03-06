package ru.yofik.bank.context.account.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yofik.bank.api.exceptions.RequestedElementAlreadyExistsException;
import ru.yofik.bank.api.exceptions.RequestedElementNotExistException;
import ru.yofik.bank.context.account.api.requests.CreateAccountRequest;
import ru.yofik.bank.context.account.api.requests.PutMoneyRequest;
import ru.yofik.bank.context.account.api.responses.CreateAccountResponse;
import ru.yofik.bank.context.account.exceptions.WrongPinCodeException;
import ru.yofik.bank.context.account.model.Account;
import ru.yofik.bank.context.account.repository.AccountRepository;
import ru.yofik.bank.context.account.view.AccountView;

import java.util.Optional;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountFactory accountFactory;

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest request) {
        if (isAccountExists(request.name, request.surname)) {
            log.warn(() -> "Account for " + request.name + " " + request.surname + " already exists");
            throw new RequestedElementAlreadyExistsException("Account already exists");
        }

        Account account = accountFactory.create(request.name, request.surname);
        accountRepository.saveAndFlush(account);

        log.info(() -> "An account with id: " + account.getAccountId() + " has been created");
        return new CreateAccountResponse(
                account.getAccountId(),
                account.getPinCode()
        );
    }

    @Override
    public void putMoney(PutMoneyRequest request, String accountId) {
        Account account = getAccount(accountId);
        checkPinCodeCorrectness(account, request.pinCode);

        account.setBalance(account.getBalance() + request.amount);
        accountRepository.saveAndFlush(account);
        log.info(() -> "New balance is " + account.getBalance());
    }

    @Override
    public AccountView getAccountInfo(String accountId, int pinCode) {
        Account account = getAccount(accountId);
        checkPinCodeCorrectness(account, pinCode);

        log.info(() -> "Returning information for account " + accountId);
        return account.toAccountView();
    }

    public Account getAccount(String accountId) {
        Optional<Account> account = accountRepository.findByAccountId(accountId);

        if (!account.isPresent()) {
            log.warn(() -> "Account " + accountId + " not exists");
            throw new RequestedElementNotExistException("Account not exists");
        }

        return account.get();
    }

    private void checkPinCodeCorrectness(Account account, int pinCode) {
        if (account.getPinCode() != pinCode) {
            log.warn(() -> "Account pinCode " + pinCode + " is wrong");
            throw new WrongPinCodeException();
        }
    }

    private boolean isAccountExists(String name, String surname) {
        return accountRepository.findByNameAndSurname(name, surname).isPresent();
    }
}
