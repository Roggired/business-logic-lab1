package ru.yofik.kickstoper.context.support.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yofik.kickstoper.api.exceptions.RequestedElementNotExistException;
import ru.yofik.kickstoper.context.mail.service.MailService;
import ru.yofik.kickstoper.context.project.repository.ProjectRepository;
import ru.yofik.kickstoper.context.support.api.request.SupportRequest;
import ru.yofik.kickstoper.context.support.exception.InvalidAmountException;
import ru.yofik.kickstoper.context.support.integration.client.BankClient;
import ru.yofik.kickstoper.context.support.integration.factory.AccountFactory;
import ru.yofik.kickstoper.context.support.integration.request.CreateTransactionRequest;
import ru.yofik.kickstoper.context.support.integration.request.GetAccountInfoRequest;
import ru.yofik.kickstoper.context.support.integration.response.GetAccountInfoResponse;
import ru.yofik.kickstoper.context.support.model.Account;
import ru.yofik.kickstoper.context.support.model.Transaction;
import ru.yofik.kickstoper.context.project.model.Project;

import java.util.Optional;

@Service
@Log4j2
public class SupportServiceImpl implements SupportService {
    @Autowired
    private BankClient bankClient;

    @Autowired
    private AccountFactory accountFactory;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MailService mailService;

    @Override
    public String support(SupportRequest request) {
        Optional<Project> projectOptional = projectRepository.findById(request.projectId);
        if (!projectOptional.isPresent()) {
            log.warn(() -> "No such project with id: " + request.projectId);
            throw new RequestedElementNotExistException("No such project with id:" + request.projectId);
        }

        Transaction transaction = new Transaction(request.accountId, request.amount);
        Project project = projectOptional.get();

        if (transaction.getAmount() <= 0) {
            log.warn(() -> "Money amount must be greater than 0");
            throw new InvalidAmountException();
        }

        CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest(
                transaction.getFromAccountId(),
                project.getFinanceData().getBankAccount(),
                transaction.getAmount()
        );

        String result = bankClient.createTransaction(createTransactionRequest).id;
        mailService.supportEmail(project);
        return result;
    }

    @Override
    public Account getAccountInfo(String accountId, int pinCode) {
        GetAccountInfoRequest getAccountInfoRequest = new GetAccountInfoRequest(
                accountId,
                pinCode
        );

        GetAccountInfoResponse getAccountInfoResponse = bankClient.getAccountInfo(getAccountInfoRequest);
        return accountFactory.createAccount(accountId, getAccountInfoResponse);
    }
}
