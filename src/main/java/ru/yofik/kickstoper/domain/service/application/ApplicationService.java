package ru.yofik.kickstoper.domain.service.application;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yofik.kickstoper.api.resources.ApplicationResource;
import ru.yofik.kickstoper.domain.entity.application.ApplicationDto;
import ru.yofik.kickstoper.domain.entity.application.ApplicationShortView;

import java.util.List;

import javax.validation.constraints.NotNull;

@Service
public interface ApplicationService {
    boolean isExists(int id);

    int createApplication(@Validated ApplicationDto applicationDto);
    void updateApplicationStatus(int id, @Validated ApplicationResource.StatusDto statusDto);

    List<ApplicationShortView> getAllApplications();
    ApplicationShortView getConcreteApplication(int id);
}
