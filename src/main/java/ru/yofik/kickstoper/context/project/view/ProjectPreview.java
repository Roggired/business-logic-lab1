package ru.yofik.kickstoper.context.project.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.yofik.kickstoper.context.application.model.Category;
import ru.yofik.kickstoper.context.application.model.Subcategory;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ProjectPreview {
    public final int id;
    public final String projectName;
    public final Category category;
    public final Subcategory subcategory;
    public final String shortDescription;
    public final long targetBudget;
    public final ZonedDateTime projectEndDate;
}
