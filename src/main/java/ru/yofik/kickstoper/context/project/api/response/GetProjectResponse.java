package ru.yofik.kickstoper.context.project.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.yofik.kickstoper.context.project.view.ProjectView;

@Getter
@AllArgsConstructor
public class GetProjectResponse {
    public final ProjectView project;
}
