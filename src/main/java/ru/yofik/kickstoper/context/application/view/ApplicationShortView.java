package ru.yofik.kickstoper.context.application.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.yofik.kickstoper.context.application.entity.Application;

@Getter
@AllArgsConstructor
public class ApplicationShortView {
    private final int id;
    private final String applicationStatus;
    private final String projectName;
    private final String category;
    private final String subcategory;
    private final String comment;

    public static ApplicationShortView fromApplication(Application application) {
        return new ApplicationShortView(
                application.getId(),
                application.getApplicationStatus().toString(),
                application.getProjectName(),
                application.getCategory().getName(),
                application.getSubcategory().getName(),
                application.getComment() == null ? null : application.getComment().getText()
        );
    }
}
