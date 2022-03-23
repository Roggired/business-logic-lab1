package ru.yofik.kickstoper.context.support.service;

import org.springframework.stereotype.Service;
import ru.yofik.kickstoper.context.support.api.request.SupportRequest;
import ru.yofik.kickstoper.context.support.model.Account;
import ru.yofik.kickstoper.context.support.model.Transaction;
import ru.yofik.kickstoper.context.project.model.Project;

@Service
public interface SupportService {
    /**
     * Returns id of created transaction in third-party bank
     */
    String support(SupportRequest request);

    Account getAccountInfo(String accountId, int pinCode);
}
