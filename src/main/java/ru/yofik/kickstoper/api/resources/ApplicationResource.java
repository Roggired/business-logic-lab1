package ru.yofik.kickstoper.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yofik.kickstoper.domain.entity.application.ApplicationDto;
import ru.yofik.kickstoper.domain.entity.application.ApplicationShortView;
import ru.yofik.kickstoper.domain.entity.application.FinanceData;
import ru.yofik.kickstoper.domain.entity.applicationFile.ApplicationFile;
import ru.yofik.kickstoper.domain.service.application.ApplicationService;

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
        return applicationService.createApplication(applicationDto);
    }

    @GetMapping("")
    public List<ApplicationShortView> getUserApplications() {
        return applicationService.getAllApplications();
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
}
