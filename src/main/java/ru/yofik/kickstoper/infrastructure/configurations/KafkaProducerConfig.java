package ru.yofik.kickstoper.infrastructure.configurations;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@Log4j2
public class KafkaProducerConfig {
    @Bean
    public Producer<String, String> configureKafkaProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka:29092");
        props.put("acks", "all");
        props.put("retries", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }
}
