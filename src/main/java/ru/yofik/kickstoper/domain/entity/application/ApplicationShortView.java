package ru.yofik.kickstoper.domain.entity.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationShortView {
    private final int id;
    private final String applicationStatus;
    private final String projectName;
    private final String category;
    private final String subcategory;

    public static ApplicationShortView fromApplication(Application application) {
        return new ApplicationShortView(
                application.getId(),
                application.getApplicationStatus().toString(),
                application.getProjectName(),
                application.getCategory().getName(),
                application.getSubcategory().getName()
        );
    }
}
