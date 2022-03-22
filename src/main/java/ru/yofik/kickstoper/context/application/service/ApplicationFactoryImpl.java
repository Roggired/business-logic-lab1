package ru.yofik.kickstoper.context.application.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yofik.kickstoper.context.application.exception.ProjectEndDateIsBeforeNowException;
import ru.yofik.kickstoper.context.application.entity.Application;
import ru.yofik.kickstoper.context.application.dto.ApplicationDto;
import ru.yofik.kickstoper.context.application.entity.ApplicationStatus;
import ru.yofik.kickstoper.context.application.entity.Category;
import ru.yofik.kickstoper.context.application.entity.Subcategory;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Creates an Application instance from ApplicationDto with an initial id equal to 0 and an application status NEW.
 * Check complex constraints. Trivial constrains should be validated by Spring Validation before colling the factory.
 *
 * @author Mr.Kefir
 */
@Component
@Log4j2
public class ApplicationFactoryImpl implements ApplicationFactory {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubcategoryService subcategoryService;


    @Override
    public @NotNull Application createApplication(ApplicationDto applicationDto) {
        Category category = categoryService.getById(applicationDto.getCategoryId());
        Subcategory subcategory = subcategoryService.getById(applicationDto.getSubcategoryId());
        ZonedDateTime projectEndDate = ZonedDateTime.ofInstant(Instant.ofEpochMilli(applicationDto.getProjectEndDate()), ZoneId.systemDefault());
        log.info(applicationDto.getProjectEndDate());
        if (projectEndDate.isBefore(ZonedDateTime.now())) {
            log.warn("Project end date " + projectEndDate + " in applicationDto has to be after the current date " + ZonedDateTime.now());
            throw new ProjectEndDateIsBeforeNowException();
        }

        return new Application(
                0,
                ApplicationStatus.NEW,
                applicationDto.getProjectName(),
                category,
                subcategory,
                applicationDto.getShortDescription(),
                applicationDto.getTargetBudget(),
                projectEndDate,
                null,
                null,
                null,
                null
        );
    }
}
