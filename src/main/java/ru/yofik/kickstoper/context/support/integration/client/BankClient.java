package ru.yofik.kickstoper.context.support.integration.client;

import org.springframework.stereotype.Service;
import ru.yofik.kickstoper.context.support.integration.request.CreateTransactionRequest;
import ru.yofik.kickstoper.context.support.integration.request.GetAccountInfoRequest;
import ru.yofik.kickstoper.context.support.integration.response.CreateTransactionResponse;
import ru.yofik.kickstoper.context.support.integration.response.GetAccountInfoResponse;

@Service
public interface BankClient {
    CreateTransactionResponse createTransaction(CreateTransactionRequest request);
    GetAccountInfoResponse getAccountInfo(GetAccountInfoRequest request);
}
