package ru.yofik.kickstoper.domain.service.application;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yofik.kickstoper.api.exceptions.ProjectNameIsNotFreeException;
import ru.yofik.kickstoper.api.exceptions.RequestedElementNotExistException;
import ru.yofik.kickstoper.api.resources.ApplicationResource;
import ru.yofik.kickstoper.domain.entity.application.Application;
import ru.yofik.kickstoper.domain.entity.application.ApplicationDto;
import ru.yofik.kickstoper.domain.entity.application.ApplicationStatus;
import ru.yofik.kickstoper.domain.entity.application.ApplicationShortView;
import ru.yofik.kickstoper.storage.sql.application.ApplicationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationFactory applicationFactory;


    @Override
    public boolean isExists(int id) {
        return applicationRepository.existsById(id);
    }

    @Override
    public int createApplication(@Validated ApplicationDto applicationDto) {
        if (!projectNameIsFree(applicationDto)) {
            log.warn(() -> "Project name: " + applicationDto.getProjectName() + " is already in use");
            throw new ProjectNameIsNotFreeException();
        }

        Application application = applicationFactory.createApplication(applicationDto);
        int newApplicationId = applicationRepository.saveAndFlush(application).getId();

        log.info(() -> "An application with id: " + newApplicationId + " has been created");
        return newApplicationId;
    }

    @Override
    public void updateApplicationStatus(int id, ApplicationResource.StatusDto statusDto) {
        if (!isExists(id)) {
            log.warn(() -> "Project id: " + id + " doesn't exist");
            throw new RequestedElementNotExistException();
        }

        int result = applicationRepository.updateStatus(id, ApplicationStatus.valueOf(statusDto.getStatus()));
        log.info(() -> "Result of updated rows is " + result);
    }

    public List<ApplicationShortView> getAllApplications() {
        List<Application> applications = applicationRepository.findAll();
        log.info("All applications has been obtained");
        return applications.stream()
                .map(application -> new ApplicationShortView(
                        application.getId(),
                        application.getApplicationStatus().toString(),
                        application.getProjectName(),
                        application.getCategory().getName(),
                        application.getSubcategory().getName()
                ))
                .collect(Collectors.toList());
    }

    private boolean projectNameIsFree(ApplicationDto applicationDto) {
        return applicationRepository.findByProjectName(applicationDto.getProjectName()) == null;
    }
}
