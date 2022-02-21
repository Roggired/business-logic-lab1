package ru.yofik.kickstoper.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yofik.kickstoper.domain.entity.application.Application;
import ru.yofik.kickstoper.domain.entity.application.ApplicationDto;
import ru.yofik.kickstoper.domain.entity.application.ApplicationShortView;
import ru.yofik.kickstoper.domain.service.application.ApplicationService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api/v1/application",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ApplicationResource {
    @Autowired
    private ApplicationService applicationService;


    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public int createApplication(@RequestBody ApplicationDto applicationDto) {
        return applicationService.createApplication(applicationDto);
    }

    @GetMapping("")
    public List<ApplicationShortView> getUserApplications() {
        return applicationService.getAllApplications();
    }
}
