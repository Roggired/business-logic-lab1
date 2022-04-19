package ru.yofik.kickstoper.context.application.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yofik.kickstoper.context.application.exception.ApplicationStatusNotSuitableException;
import ru.yofik.kickstoper.context.application.exception.ProjectNameIsNotFreeException;
import ru.yofik.kickstoper.api.exceptions.RequestedElementNotExistException;
import ru.yofik.kickstoper.context.application.dto.ApplicationDto;
import ru.yofik.kickstoper.context.application.model.*;
import ru.yofik.kickstoper.context.application.model.ApplicationFile;
import ru.yofik.kickstoper.context.application.view.ApplicationShortView;
import ru.yofik.kickstoper.context.application.repository.ApplicationFileRepository;
import ru.yofik.kickstoper.context.application.repository.ApplicationRepository;
import ru.yofik.kickstoper.context.application.repository.CommentRepository;
import ru.yofik.kickstoper.context.application.repository.FinanceDataRepository;
import ru.yofik.kickstoper.infrastructure.kafka.KafkaProducerService;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

    @Autowired
    private ApplicationFileRepository applicationFileRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;


    @Override
    public boolean isExists(int id) {
        return applicationRepository.existsById(id);
    }

    @Override
    public int create(@Validated ApplicationDto applicationDto) {
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
    public void cancel(int id, String comment) {
        Application application = getFullApplication(id);

        if (application.getApplicationStatus() != ApplicationStatus.WAIT_FOR_APPROVE) {
            log.warn(() -> "From WAIT_FOR_APPROVE status an application can be moved only in APPROVED or CANCELED statuses");
            throw new ApplicationStatusNotSuitableException();
        }

        commentRepository.saveAndFlush(
                new Comment(
                        0,
                        comment,
                        application
                )
        );
        application.setApplicationStatus(ApplicationStatus.CANCELED);
        applicationRepository.saveAndFlush(application);
        log.info(() -> "New status for application: " +  id + " is: CANCELED");
    }

    @Override
    public void approve(int id) {
        Application application = getFullApplication(id);

        if (application.getApplicationStatus() != ApplicationStatus.WAIT_FOR_APPROVE) {
            log.warn(() -> "From WAIT_FOR_APPROVE status an application can be moved only in APPROVED or CANCELED statuses");
            throw new ApplicationStatusNotSuitableException();
        }

        application.setApplicationStatus(ApplicationStatus.APPROVED);
        applicationRepository.saveAndFlush(application);
        log.info(() -> "New status for application: " +  id + " is: APPROVED");
    }

    @Override
    public void sendToApprove(int id) {
        Application application = getFullApplication(id);

        if (application.getApplicationStatus() != ApplicationStatus.NEW &&
            application.getApplicationStatus() != ApplicationStatus.CANCELED) {
            log.warn(() -> "An application can be send to approve only with statuses NEW and CANCELED");
            throw new ApplicationStatusNotSuitableException();
        }

        application.setApplicationStatus(ApplicationStatus.WAIT_FOR_APPROVE);
        applicationRepository.saveAndFlush(application);
        log.info(() -> "New status for application: " +  id + " is: WAIT_FOR_APPROVE");

        kafkaProducerService.sendMessage(
                "application-status-notification",
                "1",
                "messaaaaaaage1"
        );
        log.info("Message to Kafka has been sent");
    }

    @Override
    public void start(int id) {
        Application application = getFullApplication(id);

        if (application.getApplicationStatus() != ApplicationStatus.APPROVED) {
            log.warn(() -> "Not approved project " + application.getProjectName() + " tried to be started");
            throw new ApplicationStatusNotSuitableException();
        }

        application.setApplicationStatus(ApplicationStatus.STARTED);
        applicationRepository.saveAndFlush(application);
    }

    public List<ApplicationShortView> getAll() {
        List<Application> applications = applicationRepository.findAll();
        log.info("All applications has been obtained");
        return applications.stream()
                .map(ApplicationShortView::fromApplication)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationShortView get(int id) {
        return ApplicationShortView.fromApplication(getFullApplication(id));
    }

    @Override
    public void updateFinanceData(FinanceData financeData, int applicationId) {
        Application application = getFullApplication(applicationId);
        log.info(() -> "Application with id: " + applicationId + " has been found");

        if (updateApplicationAllowed(application.getApplicationStatus())) {
            log.warn(() -> "Finance data can be changed only for applications with statuses NEW or CANCELED");
            throw new ApplicationStatusNotSuitableException();
        }

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
        Application application = getFullApplication(applicationId);
        log.info(() -> "Application with id: " +  applicationId + " has been found");

        if (application.getFinanceData() == null) {
            log.warn(() -> "No finance data has been found for application: " + applicationId);
            throw new RequestedElementNotExistException("No finance data has been found this application");
        }

        log.info(() -> "Finance data for application: " + applicationId + " has been obtained");
        return application.getFinanceData();
    }

    @Override
    public void uploadVideo(ApplicationFile applicationFile, int applicationId) {
        Application application = getFullApplication(applicationId);
        log.info(() -> "Application with id: " + applicationId + " has been obtained");

        if (updateApplicationAllowed(application.getApplicationStatus())) {
            log.warn(() -> "Video can be changed only for applications with statuses NEW or CANCELED");
            throw new ApplicationStatusNotSuitableException();
        }

        if (application.getVideoFilename() != null) {
            applicationFileRepository.delete(application.getVideoFilename());
            log.info(() -> "Old file with video has been deleted for application: " + applicationId);
        }

        applicationFile.setFilename(hashFilename(applicationFile.getFilename()));
        applicationFileRepository.save(applicationFile);
        log.info(() -> "Saving file: " +  applicationFile.getFilename() + " on disk...");

        application.setVideoFilename(applicationFile.getFilename());
        applicationRepository.saveAndFlush(application);
        log.info(() -> "New video filename has been written to db for application: " + applicationId);
    }

    @Override
    public void uploadDescription(ApplicationFile applicationFile, int applicationId) {
        Application application = getFullApplication(applicationId);
        log.info(() -> "Application with id: " + applicationId + " has been obtained");

        if (updateApplicationAllowed(application.getApplicationStatus())) {
            log.warn(() -> "Description can be changed only for applications with statuses NEW or CANCELED");
            throw new ApplicationStatusNotSuitableException();
        }

        if (application.getDescriptionFilename() != null) {
            applicationFileRepository.delete(application.getDescriptionFilename());
            log.info(() -> "Old file with description has been deleted for application: " + applicationId);
        }

        applicationFile.setFilename(hashFilename(applicationFile.getFilename()));
        applicationFileRepository.save(applicationFile);
        log.info(() -> "Saving file: " + applicationFile.getFilename() + " on disk...");

        application.setDescriptionFilename(applicationFile.getFilename());
        applicationRepository.saveAndFlush(application);
        log.info(() -> "New description filename has been written to db for application: " + applicationId);
    }

    @Override
    public String getDescription(int applicationId) {
        Application application = getFullApplication(applicationId);
        log.info(() -> "Application with id: " + applicationId + " has been obtained");

        if (application.getDescriptionFilename() == null) {
            log.warn(() -> "Application with id: " + applicationId + " does not have a description filename");
            throw new RequestedElementNotExistException();
        }

        byte[] data = applicationFileRepository.get(application.getDescriptionFilename());
        log.info(() -> "File with description: " + application.getDescriptionFilename() + " has been read");

        return new String(data, StandardCharsets.UTF_8);
    }

    private boolean updateApplicationAllowed(ApplicationStatus applicationStatus) {
        return applicationStatus != ApplicationStatus.NEW &&
                applicationStatus != ApplicationStatus.CANCELED;
    }

    private @NotNull Application getFullApplication(int id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn(() -> "Application with id: " +  id + " does not exist");
                    return new RequestedElementNotExistException("Application with id: " + id + " does not exist");
                });
    }

    private boolean projectNameIsFree(ApplicationDto applicationDto) {
        return applicationRepository.findByProjectName(applicationDto.getProjectName()) == null;
    }

    private String hashFilename(String filename) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(filename.getBytes(StandardCharsets.UTF_8));
    }
}
