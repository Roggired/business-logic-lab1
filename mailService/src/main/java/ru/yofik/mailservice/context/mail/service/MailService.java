package ru.yofik.mailservice.context.mail.service;

import org.springframework.stereotype.Service;
import ru.yofik.mailservice.context.mail.model.Mail;

@Service
public interface MailService {
    void sendMail(Mail mail);
}
