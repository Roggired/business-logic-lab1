package ru.yofik.mailservice.context.mailTask.service;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yofik.common.MailKafkaMessage;
import ru.yofik.mailservice.context.mailTask.model.MailTask;

@Log4j2
@Component
public class KafkaMessageListener {
    private static final String TOPIC = "application-status-notification";
    private static final String GROUP_ID = "yofik-group";

    private static final Gson gson = new Gson();

    @Autowired
    private MailTaskService mailTaskService;

    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void consume(String message) {
        log.debug(() -> "Got message " + message);
        mailTaskService.handleMailRequest(gson.fromJson(message, MailKafkaMessage.class));
    }
}
