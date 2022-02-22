package ru.yofik.kickstoper.api.resources;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yofik.kickstoper.domain.entity.application.ApplicationDto;
import ru.yofik.kickstoper.domain.service.application.ApplicationService;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping(
        value = "/api/v1/application",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
public class ApplicationResource {
    @Autowired
    private ApplicationService applicationService;


    @PostMapping("")
    public int createApplication(@RequestBody ApplicationDto applicationDto) {
        return applicationService.createApplication(applicationDto);
    }

    @PostMapping("/{id}")
    public void updateApplicationStatus(@PathVariable int id, @RequestBody StatusDto statusDto) {
        applicationService.updateApplicationStatus(id, statusDto);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class StatusDto {
        @Pattern(regexp = "(NEW|WAIT_FOR_APPROVE|APPROVED|CANCELED)",
                message = "Статус должен удовлетворять спецификации")
        private String status;


    }
}
