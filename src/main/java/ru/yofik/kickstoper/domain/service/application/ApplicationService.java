package ru.yofik.kickstoper.domain.service.application;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yofik.kickstoper.domain.entity.application.ApplicationDto;
import ru.yofik.kickstoper.domain.entity.application.ApplicationShortView;
import ru.yofik.kickstoper.domain.entity.application.FinanceData;

import java.util.List;

@Service
public interface ApplicationService {
    int createApplication(@Validated ApplicationDto applicationDto);
    List<ApplicationShortView> getAllApplications();
    void updateFinanceData(@Validated FinanceData financeData, int applicationId);
    FinanceData getFinanceData(int applicationId);
}
