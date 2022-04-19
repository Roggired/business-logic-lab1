package ru.yofik.kickstoper.infrastructure.kafka;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final Producer<String, String> producer;


    public KafkaProducerService(Producer<String, String> producer) {
        this.producer = producer;
    }


    public void sendMessage(String topic, String key, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
        producer.send(record, (recordMetadata, e) -> System.out.println("Got ack from Kafka. Errors: " + e));
    }
}
