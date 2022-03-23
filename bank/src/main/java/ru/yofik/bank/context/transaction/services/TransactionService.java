package ru.yofik.bank.context.transaction.services;

import org.springframework.stereotype.Service;
import ru.yofik.bank.context.transaction.api.requests.ApproveTransactionRequest;
import ru.yofik.bank.context.transaction.api.requests.CreateTransactionRequest;
import ru.yofik.bank.context.transaction.api.responses.CreateTransactionResponse;
import ru.yofik.bank.context.transaction.view.TransactionView;

@Service
public interface TransactionService {
    CreateTransactionResponse createTransaction(CreateTransactionRequest request);
    TransactionView getTransaction(String id);
    void approveTransaction(String id, ApproveTransactionRequest request);
}
