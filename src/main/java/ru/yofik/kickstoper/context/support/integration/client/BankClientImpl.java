package ru.yofik.kickstoper.context.support.integration.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yofik.kickstoper.api.exceptions.InternalServerException;
import ru.yofik.kickstoper.context.support.exception.NoSuchAccountException;
import ru.yofik.kickstoper.context.support.integration.request.CreateTransactionRequest;
import ru.yofik.kickstoper.context.support.integration.request.GetAccountInfoRequest;
import ru.yofik.kickstoper.context.support.integration.response.CreateTransactionResponse;
import ru.yofik.kickstoper.context.support.integration.response.GetAccountInfoResponse;
import ru.yofik.kickstoper.infrastructure.http.HttpRequest;
import ru.yofik.kickstoper.infrastructure.http.HttpResponse;

@Service
@Log4j2
public class BankClientImpl implements BankClient {
    @Autowired
    private HttpRequest httpRequest;


    @Override
    public CreateTransactionResponse createTransaction(CreateTransactionRequest request) {
        HttpResponse httpResponse =
                httpRequest.postRequest("http://bank:65101/api/v2/transactions", request)
                .execute();

        if (httpResponse.getStatusCode() == 201) {
            return httpResponse.getContent(CreateTransactionResponse.class);
        } else if (httpResponse.isBadRequest()) {
            throw new NoSuchAccountException();
        } else {
            log.fatal(() -> "Unexpected exception while requesting Bank:\n" + httpResponse.getContent(String.class));
            throw new InternalServerException();
        }
    }

    @Override
    public GetAccountInfoResponse getAccountInfo(GetAccountInfoRequest request) {
        HttpResponse httpResponse =
                httpRequest.getRequest("http://bank:65101/api/v2/account/" + request.getAccountId() + "?pinCode=" + request.getPinCode())
                        .execute();

        if (httpResponse.isOk()) {
            return httpResponse.getContent(GetAccountInfoResponse.class);
        } else if (httpResponse.isBadRequest()) {
            throw new NoSuchAccountException();
        } else {
            log.fatal(() -> "Unexpected exception while requesting Bank:\n" + httpResponse.getContent(String.class));
            throw new InternalServerException();
        }
    }
}
