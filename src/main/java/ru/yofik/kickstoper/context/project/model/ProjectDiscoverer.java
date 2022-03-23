package ru.yofik.kickstoper.context.project.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.yofik.kickstoper.context.project.repository.ProjectRepository;
import ru.yofik.kickstoper.context.support.integration.client.BankClient;
import ru.yofik.kickstoper.context.support.integration.request.GetAccountInfoRequest;
import ru.yofik.kickstoper.context.support.integration.response.GetAccountInfoResponse;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectDiscoverer {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BankClient bankClient;


    @Scheduled(fixedDelay = 30000L)
    public void discoverBalanceOfAccountsAssociatedWithProjects() {
        List<Project> projects = projectRepository.findAll();

        for (Project project : projects) {
            GetAccountInfoRequest request = new GetAccountInfoRequest(
                    project.getFinanceData().getBankAccount(),
                    1111
            );

            GetAccountInfoResponse getAccountInfoResponse = bankClient.getAccountInfo(request);
            project.setBalance(getAccountInfoResponse.balance);
        }

        projectRepository.saveAllAndFlush(projects);
    }

    @Scheduled(fixedDelay = 30000L)
    public void deleteFinishedProjects() {
        List<Project> projects = projectRepository.findAll();
        List<Project> toBeRemoved = new ArrayList<>();

        for (Project project : projects) {
            if (project.getProjectEndDate().isBefore(ZonedDateTime.now())) {
                toBeRemoved.add(project);
            }
        }

        projectRepository.deleteAll(toBeRemoved);
    }
}
