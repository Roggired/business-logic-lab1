package ru.yofik.kickstoper.context.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yofik.common.MailKafkaMessage;
import ru.yofik.kickstoper.context.application.model.Application;
import ru.yofik.kickstoper.context.mail.model.MailTask;
import ru.yofik.kickstoper.context.mail.repository.MailTaskRepository;
import ru.yofik.kickstoper.context.project.model.Project;
import ru.yofik.kickstoper.infrastructure.kafka.KafkaProducerService;

import java.util.Collections;

@Service
public class MailService {
    private static final String APPLICATION_STATUS_TOPIC = "application-status-notification";

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private MailTaskRepository mailTaskRepository;


    public void newApplicationToApproveEmail(Application application) {
        MailTask mailTask = new MailTask();
        mailTask.setTitle("New application waits for approve");
        mailTask.setBody("ВИКТОР, ПОЖАЛУЙСТА, ЗААПРУВЬ applicationId: " + application.getId());
        mailTask.setRecipients(Collections.singletonList("megamozg@tune-it.ru"));
        mailTask = mailTaskRepository.saveAndFlush(mailTask);

        kafkaProducerService.sendMessage(
                APPLICATION_STATUS_TOPIC,
                application.getId() + "_notification_status_" + application.getApplicationStatus(),
                new MailKafkaMessage(
                        mailTask.getId()
                )
        );
    }

    public void applicationHasBeenApprovedEmail(Application application) {
        MailTask mailTask = new MailTask();
        mailTask.setTitle("Your application has been approved");
        mailTask.setBody("ОАОАОАОАОАОАОАО, УРА!!!! ЗААПРУВЛЕНО, СЛИВАЕМ В DEV applicationId: " + application.getId());
        mailTask.setRecipients(Collections.singletonList("junior.pomidor.developer@tune-it.ru"));
        mailTask = mailTaskRepository.saveAndFlush(mailTask);

        kafkaProducerService.sendMessage(
                APPLICATION_STATUS_TOPIC,
                application.getId() + "_notification_status_" + application.getApplicationStatus(),
                new MailKafkaMessage(
                        mailTask.getId()
                )
        );
    }

    public void applicationHasBeenCanceledEmail(Application application) {
        MailTask mailTask = new MailTask();
        mailTask.setTitle("Your... application... has been... c a n c e l e d");
        mailTask.setBody("о нет(((((((((((((((((((((((((((((((( applicationId: " + application.getId());
        mailTask.setRecipients(Collections.singletonList("junior.pomidor.developer@tune-it.ru"));
        mailTask = mailTaskRepository.saveAndFlush(mailTask);

        kafkaProducerService.sendMessage(
                APPLICATION_STATUS_TOPIC,
                application.getId() + "_notification_status_" + application.getApplicationStatus(),
                new MailKafkaMessage(
                        mailTask.getId()
                )
        );
    }

    public void supportEmail(Project project) {
        MailTask mailTask = new MailTask();
        mailTask.setTitle("Возмоооооожно, вам пожертвовали");
        mailTask.setBody("Никто не знает наверняка, но похоже вас решили поддержать projectId: " + project.getId());
        mailTask.setRecipients(Collections.singletonList("junior.pomidor.developer@tune-it.ru"));
        mailTask = mailTaskRepository.saveAndFlush(mailTask);

        kafkaProducerService.sendMessage(
                APPLICATION_STATUS_TOPIC,
                project.getId() + "_support",
                new MailKafkaMessage(
                        mailTask.getId()
                )
        );
    }
}
