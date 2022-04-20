package ru.yofik.mailservice.context.mailTask.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yofik.common.MailKafkaMessage;
import ru.yofik.mailservice.context.mail.model.Mail;
import ru.yofik.mailservice.context.mail.service.MailService;
import ru.yofik.mailservice.context.mailTask.model.MailTask;
import ru.yofik.mailservice.context.mailTask.repository.MailTaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class MailTaskServiceImpl implements MailTaskService {
    @Autowired
    private MailTaskRepository repository;

    @Autowired
    private MailService mailService;

    @Override
    public void handleMailRequest(MailKafkaMessage message) {
        log.debug(() -> "Got message with id " + message.mailTaskId);

        MailTask task = getMailTask(message.mailTaskId);
        if (task == null) return;
        log.debug(() -> "Got task " + task);

        List<Mail> mails = createMails(task);
        mails.forEach(mailService::sendMail);
        log.debug(() -> "All messages was sent");

        repository.delete(task);
        log.debug(() -> "Task was deleted from DB");
    }

    private MailTask getMailTask(int id) {
        Optional<MailTask> mailTask = repository.findById(id);

        if (!mailTask.isPresent()) {
            log.warn("Mail task with id " + id + " not exists");
            return null;
        }

        return mailTask.get();
    }

    private List<Mail> createMails(MailTask task) {
        return task.getRecipients().stream()
                .map((recipient) -> new Mail(
                        recipient,
                        task.getTitle(),
                        task.getBody()
                ))
                .collect(Collectors.toList());
    }
}
