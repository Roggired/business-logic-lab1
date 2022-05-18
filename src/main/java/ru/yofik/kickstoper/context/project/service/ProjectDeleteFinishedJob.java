package ru.yofik.kickstoper.context.project.service;

import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ProjectDeleteFinishedJob implements Job {
    public static final String JOB_NAME = "project-delete-finished-job";

    private final ProjectObserverService projectObserverService;

    public ProjectDeleteFinishedJob(ProjectObserverService projectObserverService) {
        this.projectObserverService = projectObserverService;
    }

    @Override
    public void execute(JobExecutionContext context) {
        TriggerKey triggerKey = context.getTrigger().getKey();
        log.info(() -> "Started ProjectDeleteFinished job " + triggerKey);

        try {
            projectObserverService.deleteFinishedProjects();
        } catch (Exception e) {
            log.error(() -> "job " + triggerKey + "execution failed", e);
            throw e;
        }
    }
}