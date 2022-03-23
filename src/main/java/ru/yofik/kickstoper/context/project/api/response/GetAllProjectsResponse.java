package ru.yofik.kickstoper.context.project.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.yofik.kickstoper.context.project.view.ProjectPreview;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetAllProjectsResponse {
    public final List<ProjectPreview> projects;
}
