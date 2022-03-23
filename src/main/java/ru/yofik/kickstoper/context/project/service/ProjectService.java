package ru.yofik.kickstoper.context.project.service;

import org.springframework.stereotype.Service;
import ru.yofik.kickstoper.context.project.api.response.GetAllProjectsResponse;
import ru.yofik.kickstoper.context.project.api.response.GetProjectResponse;

@Service
public interface ProjectService {
    void createProject(int applicationId);
    GetAllProjectsResponse getProjectsByName(String name);
    GetProjectResponse getProjectById(int id);
    GetAllProjectsResponse getProjectsByCategory(int categoryId);
    GetAllProjectsResponse getProjectsBySubcategory(int subcategoryId);
}
