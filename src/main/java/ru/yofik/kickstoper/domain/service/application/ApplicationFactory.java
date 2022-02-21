package ru.yofik.kickstoper.domain.service.application;

import org.springframework.stereotype.Component;
import ru.yofik.kickstoper.domain.entity.application.Application;
import ru.yofik.kickstoper.domain.entity.application.ApplicationDto;

import javax.validation.constraints.NotNull;

@Component
public interface ApplicationFactory {
    @NotNull Application createApplication(ApplicationDto applicationDto);
}
