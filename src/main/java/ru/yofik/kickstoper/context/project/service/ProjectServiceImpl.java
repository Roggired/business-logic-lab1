package ru.yofik.kickstoper.context.project.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.jta.JtaTransactionManager;
import ru.yofik.kickstoper.api.exceptions.RequestedElementNotExistException;
import ru.yofik.kickstoper.context.application.model.Application;
import ru.yofik.kickstoper.context.application.model.ApplicationStatus;
import ru.yofik.kickstoper.context.application.model.Category;
import ru.yofik.kickstoper.context.application.model.Subcategory;
import ru.yofik.kickstoper.context.application.repository.ApplicationRepository;
import ru.yofik.kickstoper.context.application.repository.CategoryRepository;
import ru.yofik.kickstoper.context.application.repository.SubcategoryRepository;
import ru.yofik.kickstoper.context.project.api.response.GetAllProjectsResponse;
import ru.yofik.kickstoper.context.project.api.response.GetProjectResponse;
import ru.yofik.kickstoper.context.project.exception.ApplicationHasWrongStatusException;
import ru.yofik.kickstoper.context.project.factory.ProjectFactory;
import ru.yofik.kickstoper.context.project.model.Project;
import ru.yofik.kickstoper.context.project.repository.ProjectRepository;
import ru.yofik.kickstoper.infrastructure.transaction.KicktoperTransactionManager;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    @Autowired
    private ProjectFactory projectFactory;

    @Autowired
    private KicktoperTransactionManager kicktoperTransactionManager;


    @Override
    public void createProject(int applicationId) {
        Optional<Application> application = applicationRepository.findById(applicationId);
        if (!application.isPresent()) {
            log.warn(() -> "Application with id: " + applicationId + " doesn't exist");
            throw new RequestedElementNotExistException("Application with id: " + applicationId + " doesn't exist");
        }

        if (application.get().getApplicationStatus() != ApplicationStatus.APPROVED) {
            log.warn(() -> "Application with id: " + applicationId + " has wrong status: " + application.get().getApplicationStatus());
            throw new ApplicationHasWrongStatusException("Application with id: " + applicationId + " has wrong status: " + application.get().getApplicationStatus());
        }


        Project project = projectFactory.createFromRequest(application.get());

        kicktoperTransactionManager.begin();
        projectRepository.save(project);
        log.info(() -> "Project from application id: " + applicationId + " has been created");
        applicationRepository.delete(application.get());
        log.info(() -> "Application id: " + applicationId + " has been deleted");
        kicktoperTransactionManager.commit();
    }

    @Override
    public GetAllProjectsResponse getProjectsByName(String name) {
        return new GetAllProjectsResponse(
                projectRepository.findAllByProjectNameContains(name)
                        .stream()
                        .map(Project::getPreview)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public GetProjectResponse getProjectById(int id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RequestedElementNotExistException("Project with id: " + id + " not found"));
        return new GetProjectResponse(
                project.getView()
        );
    }

    @Override
    public GetAllProjectsResponse getProjectsByCategory(int categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            log.warn(() -> "Category with id : " + categoryId + " doesn't exist");
            throw new RequestedElementNotExistException("Category with id : " + categoryId + " doesn't exist");
        }

        return new GetAllProjectsResponse(
                projectRepository.findAllByCategoryId(categoryId)
                        .stream()
                        .map(Project::getPreview)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public GetAllProjectsResponse getProjectsBySubcategory(int subcategoryId) {
        Optional<Subcategory> subcategory = subcategoryRepository.findById(subcategoryId);
        if (!subcategory.isPresent()) {
            log.warn(() -> "Subcategory with id : " + subcategoryId + " doesn't exist");
            throw new RequestedElementNotExistException("Subcategory with id : " + subcategoryId + " doesn't exist");
        }

        int categoryId = subcategory.get().getCategory().getId();
        return new GetAllProjectsResponse(
                projectRepository.findAllByCategoryIdAndSubcategoryId(categoryId, subcategoryId)
                        .stream()
                        .map(Project::getPreview)
                        .collect(Collectors.toList())
        );
    }
}
