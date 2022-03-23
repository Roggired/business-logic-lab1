package ru.yofik.kickstoper.context.support.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yofik.kickstoper.context.support.api.request.SupportRequest;
import ru.yofik.kickstoper.context.support.service.SupportService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v2/support")
public class SupportResource {
    @Autowired
    private SupportService supportService;


    @PostMapping
    public ResponseEntity<?> support(@RequestBody @Valid SupportRequest request) {
        String transactionId = supportService.support(request);
        return ResponseEntity
                .status(HttpStatus.SEE_OTHER)
                .header("Location", "http://localhost:65101/api/v2/transactions/" + transactionId)
                .build();
    }
}
