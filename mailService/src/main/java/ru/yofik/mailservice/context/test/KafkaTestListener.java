package ru.yofik.mailservice.context.test;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaTestListener {
    @KafkaListener(topics = "application-status-notification", groupId = "yofik-group")
    public void consume(String message) {
        System.out.println("Received Message in group yofik-group: " + message);
    }
}
