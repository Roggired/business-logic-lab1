package ru.yofik.kickstoper.context.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProjectScheduledTasks {
    @Autowired
    private ProjectObserverService projectObserverService;

//    @Scheduled(fixedDelay = 30000L)
    public void discoverBalanceOfAccountsAssociatedWithProjects() {
        projectObserverService.discoverBalanceOfAccountsAssociatedWithProjects();
    }

//    @Scheduled(fixedDelay = 30000L)
    public void deleteFinishedProjects() {
        projectObserverService.deleteFinishedProjects();
    }
}
