package ru.yofik.kickstoper.infrastructure.kafka;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.yofik.common.MailKafkaMessage;

@Service
@Log4j2
public class KafkaProducerService {
    private final Producer<String, String> producer;


    public KafkaProducerService(Producer<String, String> producer) {
        this.producer = producer;
    }


    public void sendMessage(String topic, String key, MailKafkaMessage message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, new Gson().toJson(message));
        log.info("New Producer record: " + record);
        producer.send(record, (recordMetadata, e) -> {
            log.info("Got ack from Kafka. Errors: " + e);
        });
    }
}
