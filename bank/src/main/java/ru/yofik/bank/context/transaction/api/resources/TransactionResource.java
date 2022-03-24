package ru.yofik.bank.context.transaction.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yofik.bank.context.transaction.api.requests.ApproveTransactionRequest;
import ru.yofik.bank.context.transaction.api.requests.CreateTransactionRequest;
import ru.yofik.bank.context.transaction.api.responses.CreateTransactionResponse;
import ru.yofik.bank.context.transaction.services.TransactionService;
import ru.yofik.bank.context.transaction.view.TransactionView;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v2/transactions")
public class TransactionResource {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateTransactionResponse createTransaction(@RequestBody @Valid CreateTransactionRequest request) {
        return transactionService.createTransaction(request);
    }

    @GetMapping(value = "/{id}")
    public TransactionView getTransaction(@PathVariable String id) {
        return transactionService.getTransaction(id);
    }

    @PostMapping(value = "/{id}")
    public void approveTransaction(@PathVariable String id, @RequestBody @Valid ApproveTransactionRequest request) {
        transactionService.approveTransaction(id, request);
    }
}
