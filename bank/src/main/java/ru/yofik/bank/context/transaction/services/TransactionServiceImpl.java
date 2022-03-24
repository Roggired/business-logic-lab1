package ru.yofik.bank.context.transaction.services;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
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

import javax.transaction.*;
import java.util.NoSuchElementException;

@Log4j2
@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JtaTransactionManager transactionManager;

    @Override
    public CreateTransactionResponse createTransaction(CreateTransactionRequest request) {
        if (!accountService.isAccountExists(request.fromAccount)) {
            log.warn(() -> "From account " + request.fromAccount + " not exists");
            throw new RequestedElementNotExistException("Account not exists");
        }

        if (!accountService.isAccountExists(request.toAccount)) {
            log.warn(() -> "To account " + request.toAccount + " not exists");
            throw new RequestedElementNotExistException("Account not exists");
        }

        Transaction transaction = new Transaction("", request.fromAccount, request.toAccount, request.amount);
        String generatedId = transactionRepository.save(transaction).getId();

        log.info(() -> "Transaction with id " + generatedId + " was created");
        return new CreateTransactionResponse(generatedId);
    }

    @Override
    public TransactionView getTransaction(String id) {
        if (!isExists(id)) {
            log.warn(() -> "Transaction " + id + " not exists");
            throw new RequestedElementNotExistException("Transaction not exists");
        }

        Transaction transaction = transactionRepository.findById(id).get();
        log.info(() -> "Returning transaction with ID " + transaction.getId());
        return TransactionView.from(transaction);
    }

    @Override
    public void approveTransaction(String id, ApproveTransactionRequest request)  {
        if (!isExists(id)) {
            log.warn(() -> "Transaction " + id + " not exists");
            throw new RequestedElementNotExistException("Transaction not exists");
        }

        Transaction transaction = transactionRepository.findById(id).get();
        log.info(() -> "Got transaction with ID " + transaction.getId());

        Account fromAccount = accountRepository.findByAccountId(transaction.getFromAccount());
        Account toAccount = accountRepository.findByAccountId(transaction.getToAccount());

        if (!doAccountHasMoneyForTransaction(fromAccount, transaction)) {
            log.warn(() -> "Account " + fromAccount.getAccountId() + " doesnt have money for transaction");
            throw new NotEnoughMoneyForTransaction("From account doesnt have enough money");
        }

        try {
            transactionManager.getTransactionManager().begin();

            fromAccount.setBalance(fromAccount.getBalance() - transaction.getAmount());
            toAccount.setBalance(toAccount.getBalance() + transaction.getAmount());

            accountRepository.saveAndFlush(fromAccount);
            accountRepository.saveAndFlush(toAccount);

            transactionManager.getTransactionManager().commit();
        } catch (Exception e) {
            try {
                transactionManager.getTransactionManager().rollback();
            } catch (SystemException ex) {
                ex.printStackTrace();
            }

            throw new InternalServerException();
        }
    }

    private boolean doAccountHasMoneyForTransaction(Account account, Transaction transaction) {
        return account.getBalance() - transaction.getAmount() > 0;
    }


    private boolean isExists(String id) {
        try {
            transactionRepository.findById(id).get();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
