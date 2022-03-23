package ru.yofik.kickstoper.context.project.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yofik.kickstoper.context.project.api.request.SupportProjectRequest;
import ru.yofik.kickstoper.context.project.api.response.GetAllProjectsResponse;
import ru.yofik.kickstoper.context.project.api.response.GetProjectResponse;
import ru.yofik.kickstoper.context.project.service.ProjectService;

@RestController
@RequestMapping("/api/v2/projects")
public class ProjectResource {
    @Autowired
    private ProjectService projectService;


    @PostMapping("/{id}/support")
    public void supportProject(@RequestParam("id") int projectId, @RequestBody SupportProjectRequest request) {

    }

    @PostMapping("/publish")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProject(@RequestParam("applicationId") int applicationId) {
        projectService.createProject(applicationId);
    }

    @GetMapping("/{id}")
    public GetProjectResponse getProject(@PathVariable("id") int projectId) {
        return projectService.getProjectById(projectId);
    }

    @GetMapping
    public GetAllProjectsResponse getAllProjects(@RequestParam("name") String name) {
        return projectService.getProjectsByName(name);
    }

    @GetMapping("/filterByCategory")
    public GetAllProjectsResponse getAllProjectsByCategory(@RequestParam("categoryId") int categoryId) {
        return projectService.getProjectsByCategory(categoryId);
    }

    @GetMapping("/filterBySubcategory")
    public GetAllProjectsResponse getAllProjectsBySubcategory(@RequestParam("subcategoryId") int subcategoryId) {
        return projectService.getProjectsBySubcategory(subcategoryId);
    }
}
