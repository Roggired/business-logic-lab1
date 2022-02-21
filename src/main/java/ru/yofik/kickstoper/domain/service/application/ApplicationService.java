package ru.yofik.kickstoper.domain.service.application;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yofik.kickstoper.domain.entity.application.ApplicationDto;

@Service
public interface ApplicationService {
    int createApplication(@Validated ApplicationDto applicationDto);
}
