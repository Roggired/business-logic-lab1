package ru.yofik.kickstoper.context.project.factory;

import org.springframework.stereotype.Component;
import ru.yofik.kickstoper.context.application.model.Application;
import ru.yofik.kickstoper.context.project.model.Project;

@Component
public class ProjectFactory {
    public Project createFromRequest(Application application) {
        Project project = new Project();
        project.setProjectName(application.getProjectName());
        project.setProjectEndDate(application.getProjectEndDate());
        project.setCategory(application.getCategory());
        project.setSubcategory(application.getSubcategory());
        project.setFinanceData(application.getFinanceData());
        project.setDescriptionFilename(application.getDescriptionFilename());
        project.setShortDescription(application.getShortDescription());
        project.setVideoFilename(application.getVideoFilename());
        project.setTargetBudget(application.getTargetBudget());
        return project;
    }
}
