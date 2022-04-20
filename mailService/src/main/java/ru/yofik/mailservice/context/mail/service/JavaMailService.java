package ru.yofik.mailservice.context.mail.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.yofik.mailservice.context.mail.model.Mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@Log4j2
public class JavaMailService implements MailService {
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendMail(Mail mail) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.setTo(mail.getRecipient());
            helper.setFrom("test@test");
            helper.setSubject(mail.getTitle());
            helper.setText(mail.getBody(), true);
            emailSender.send(message);

            log.info(() -> "Email with subject: " + mail.getTitle() + " from: " + "test@test" + " to: " + mail.getRecipient() + " has been sent");
        } catch (MessagingException e) {
            log.error("Can't send email to: " + mail.getRecipient(), e);
            throw new RuntimeException(e);
        }
    }
}
