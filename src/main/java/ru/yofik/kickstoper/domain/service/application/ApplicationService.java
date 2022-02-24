package ru.yofik.kickstoper.domain.service.application;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yofik.kickstoper.domain.entity.application.ApplicationDto;
import ru.yofik.kickstoper.domain.entity.application.ApplicationShortView;
import ru.yofik.kickstoper.domain.entity.application.FinanceData;
import ru.yofik.kickstoper.domain.entity.applicationFile.ApplicationFile;

import java.util.List;

@Service
public interface ApplicationService {
    boolean isExists(int id);

    int create(@Validated ApplicationDto applicationDto);
    void cancel(int id, String comment);
    void approve(int id);
    void sendToApprove(int id);
    void start(int id);

    List<ApplicationShortView> getAll();
    ApplicationShortView get(int id);
    void updateFinanceData(@Validated FinanceData financeData, int applicationId);
    FinanceData getFinanceData(int applicationId);
    void uploadVideo(ApplicationFile applicationFile, int applicationId);
    void uploadDescription(ApplicationFile applicationFile, int applicationId);
    String getDescription(int applicationId);
}
