package ru.yofik.mailservice.context.mailTask.service;

import org.springframework.stereotype.Service;
import ru.yofik.common.MailKafkaMessage;
import ru.yofik.mailservice.context.mailTask.model.MailTask;

@Service
public interface MailTaskService {
    void handleMailRequest(MailKafkaMessage message);
}
