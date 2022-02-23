package ru.yofik.kickstoper.domain.service.application;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yofik.kickstoper.api.exceptions.ProjectNameIsNotFreeException;
import ru.yofik.kickstoper.api.exceptions.RequestedElementNotExistException;
import ru.yofik.kickstoper.domain.entity.application.Application;
import ru.yofik.kickstoper.domain.entity.application.ApplicationDto;
import ru.yofik.kickstoper.domain.entity.application.ApplicationShortView;
import ru.yofik.kickstoper.domain.entity.application.FinanceData;
import ru.yofik.kickstoper.storage.sql.application.ApplicationRepository;
import ru.yofik.kickstoper.storage.sql.application.FinanceDataRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private FinanceDataRepository financeDataRepository;

    @Autowired
    private ApplicationFactory applicationFactory;


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

    @Override
    public void updateFinanceData(FinanceData financeData, int applicationId) {
        Application application = getApplication(applicationId);
        log.info(() -> "Application with id: " + applicationId + " has been found");

        if (application.getFinanceData() != null) {
            application.getFinanceData().setBankName(financeData.getBankName());
            application.getFinanceData().setBankAccount(financeData.getBankAccount());
            application.getFinanceData().setBankCountry(financeData.getBankCountry());
            financeDataRepository.saveAndFlush(application.getFinanceData());
            log.info(() -> "Finance data has been updated for application: " + applicationId);
        } else {
            financeData = financeDataRepository.save(financeData);
            application.setFinanceData(financeData);
            applicationRepository.saveAndFlush(application);
            log.info(() -> "Finance data has been added to application: " + applicationId);
        }
    }

    @Override
    public FinanceData getFinanceData(int applicationId) {
        Application application = getApplication(applicationId);
        log.info(() -> "Application with id: " +  applicationId + " has been found");

        if (application.getFinanceData() == null) {
            log.warn(() -> "No finance data has been found for application: " + applicationId);
            throw new RequestedElementNotExistException("No finance data has been found this application");
        }

        log.info(() -> "Finance data for application: " + applicationId + " has been obtained");
        return application.getFinanceData();
    }

    private @NotNull Application getApplication(int id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn(() -> "Application with id: " +  id + " does not exist");
                    return new RequestedElementNotExistException("Application with id: " + id + " does not exist");
                });
    }

    private boolean projectNameIsFree(ApplicationDto applicationDto) {
        return applicationRepository.findByProjectName(applicationDto.getProjectName()) == null;
    }
}
