package ru.yofik.bank.context.transaction.services;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.JtaTransactionManager;
import ru.yofik.bank.api.exceptions.InternalServerException;
import ru.yofik.bank.api.exceptions.RequestedElementNotExistException;
import ru.yofik.bank.context.account.model.Account;
import ru.yofik.bank.context.account.repository.AccountRepository;
import ru.yofik.bank.context.account.services.AccountService;
import ru.yofik.bank.context.transaction.api.requests.ApproveTransactionRequest;
import ru.yofik.bank.context.transaction.api.requests.CreateTransactionRequest;
import ru.yofik.bank.context.transaction.api.responses.CreateTransactionResponse;
import ru.yofik.bank.context.transaction.exceptions.NotEnoughMoneyForTransaction;
import ru.yofik.bank.context.transaction.model.Transaction;
import ru.yofik.bank.context.transaction.repository.TransactionRepository;
import ru.yofik.bank.context.transaction.view.TransactionView;

import javax.transaction.SystemException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public CreateTransactionResponse createTransaction(CreateTransactionRequest request) {
        Account fromAccount = accountService.getAccount(request.fromAccount);
        Account toAccount = accountService.getAccount(request.toAccount);

        Transaction transaction = new Transaction(UUID.randomUUID().toString(), fromAccount.getAccountId(), toAccount.getAccountId(), request.amount);
        String generatedId = transactionRepository.save(transaction).getId();

        log.info(() -> "Transaction with id " + generatedId + " was created");
        return new CreateTransactionResponse(generatedId);
    }

    @Override
    public TransactionView getTransaction(String id) {
        Transaction transaction = getTransactionFromDB(id);
        log.info(() -> "Returning transaction with ID " + transaction.getId());
        return transaction.toTransactionView();
    }

    @Override
    @Transactional
    public void approveTransaction(String id, ApproveTransactionRequest request) {
        Transaction transaction = getTransactionFromDB(id);
        log.info(() -> "Got transaction with ID " + transaction.getId());

        Account fromAccount = accountService.getAccount(transaction.getFromAccount());
        Account toAccount = accountService.getAccount(transaction.getToAccount());

        if (!doAccountHasMoneyForTransaction(fromAccount, transaction)) {
            log.warn(() -> "Account " + fromAccount.getAccountId() + " doesnt have money for transaction");
            throw new NotEnoughMoneyForTransaction("From account doesnt have enough money");
        }

        fromAccount.setBalance(fromAccount.getBalance() - transaction.getAmount());
        toAccount.setBalance(toAccount.getBalance() + transaction.getAmount());

        accountRepository.saveAllAndFlush(Arrays.asList(fromAccount, toAccount));
        transactionRepository.delete(transaction);
    }

    private boolean doAccountHasMoneyForTransaction(Account account, Transaction transaction) {
        return account.getBalance() - transaction.getAmount() > 0;
    }


    private Transaction getTransactionFromDB(String id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);

        if (!transaction.isPresent()) {
            log.warn(() -> "Transaction " + id + " not exists");
            throw new RequestedElementNotExistException("Transaction not exists");
        }

        return transaction.get();
    }

}
