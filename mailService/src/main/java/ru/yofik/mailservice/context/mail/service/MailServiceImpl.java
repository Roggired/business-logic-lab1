package ru.yofik.mailservice.context.mail.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.yofik.mailservice.context.mail.model.Mail;

@Log4j2
@Service
@Primary
public class MailServiceImpl implements MailService {
    @Override
    public void sendMail(Mail mail) {
        log.debug(() -> "Mail to " + mail.getRecipient() + " was successfully sent");
        log.debug(() -> "Mail title is " + mail.getTitle());
        log.debug(() -> "Mail body is " + mail.getBody());
    }
}
