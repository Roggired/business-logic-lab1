package ru.yofik.mailservice.context.mailTask.service;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import ru.yofik.common.MailKafkaMessage;

import java.time.Duration;

@Log4j2
@Component
@ApplicationScope
public class KafkaMessageListener {
    private static final Gson gson = new Gson();

    @Autowired
    private MailTaskService mailTaskService;

    @Autowired
    private KafkaConsumer<String, String> kafkaConsumer;

    public void init() {
        Worker worker = new Worker(
                kafkaConsumer,
                mailTaskService
        );
        worker.setDaemon(true);
        worker.start();
    }

//    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
//    public void consume(String message) {
//        log.debug(() -> "Got message " + message);
//        mailTaskService.handleMailRequest(gson.fromJson(message, MailKafkaMessage.class));
//    }


    private static class Worker extends Thread {
        private final KafkaConsumer<String, String> consumer;
        private final MailTaskService mailTaskService;

        private Worker(KafkaConsumer<String, String> consumer, MailTaskService mailTaskService) {
            this.consumer = consumer;
            this.mailTaskService = mailTaskService;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    String message = record.value();
                    mailTaskService.handleMailRequest(gson.fromJson(message, MailKafkaMessage.class));
                }
            }
        }
    }
}
