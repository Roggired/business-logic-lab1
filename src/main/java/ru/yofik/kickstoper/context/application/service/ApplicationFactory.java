package ru.yofik.kickstoper.context.application.service;

import org.springframework.stereotype.Component;
import ru.yofik.kickstoper.context.application.model.Application;
import ru.yofik.kickstoper.context.application.dto.ApplicationDto;

import javax.validation.constraints.NotNull;

@Component
public interface ApplicationFactory {
    @NotNull Application createApplication(ApplicationDto applicationDto);
}
