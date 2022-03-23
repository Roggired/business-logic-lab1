package ru.yofik.kickstoper.context.application.api;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yofik.kickstoper.context.application.dto.ApplicationDto;
import ru.yofik.kickstoper.context.application.view.ApplicationShortView;
import ru.yofik.kickstoper.context.application.entity.FinanceData;
import ru.yofik.kickstoper.context.application.entity.ApplicationFile;
import ru.yofik.kickstoper.context.application.service.ApplicationService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.IOException;
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
        return applicationService.create(applicationDto);
    }

    @PostMapping(value = "/{id}/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void cancelApplication(@PathVariable int id, @RequestBody @Valid CommentDto commentDto) {
        applicationService.cancel(id, commentDto.comment);
    }

    @PostMapping(value = "/{id}/approve")
    public void approveApplication(@PathVariable int id) {
        applicationService.approve(id);
    }

    @PostMapping(value = "/{id}/sendToApprove")
    public void sendToApproveApplication(@PathVariable int id) {
        applicationService.sendToApprove(id);
    }

    @GetMapping("")
    public List<ApplicationShortView> getUserApplications() {
        return applicationService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ApplicationShortView getConcreteApplication(@PathVariable int id) {
        return applicationService.get(id);
    }

    @PostMapping(value = "/{id}/start")
    public void startApplication(@PathVariable int id) {
        applicationService.start(id);
    }

    @PutMapping(value = "/{id}/finances", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateFinanceData(@PathVariable int id, @RequestBody FinanceData financeData) {
        applicationService.updateFinanceData(financeData, id);
    }

    @GetMapping(value = "/{id}/finances")
    public FinanceData getFinanceData(@PathVariable int id) {
        return applicationService.getFinanceData(id);
    }

    @PutMapping(value = "/{id}/video", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadVideo(@PathVariable int id, MultipartFile multipartFile) {
        try {
            applicationService.uploadVideo(
                    new ApplicationFile(
                            multipartFile.getName() + "-application-video-" + id,
                            multipartFile.getBytes()
                    ),
                    id
            );
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}/description", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadDescription(@PathVariable int id, @RequestParam("file") MultipartFile multipartFile) {
        try {
            applicationService.uploadDescription(
                    new ApplicationFile(
                            multipartFile.getName() + "-application-description-" + id,
                            multipartFile.getBytes()
                    ),
                    id
            );
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}/description")
    public String getDescription(@PathVariable int id) {
        return applicationService.getDescription(id);
    }

    @Data
    public static class CommentDto {
        @NotBlank
        private String comment;
    }
}
