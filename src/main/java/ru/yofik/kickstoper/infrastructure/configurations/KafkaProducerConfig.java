package ru.yofik.kickstoper.infrastructure.configurations;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
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

//    @SneakyThrows
//    @Bean
//    public AdminClient kafkaAdminClient() {
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "localhost:9092");
//        AdminClient adminClient = KafkaAdminClient.create(props);
//        adminClient.describeCluster().clusterId().whenComplete((s, e) -> log.info("Describe cluster: " + s));
//        Config config = adminClient.createTopics(
//                Collections.singletonList(
//                        new NewTopic("application-status-notification", 1, (short)1)
//                )
//        ).config("application-status-notification").get();
//        log.info(config.entries());
//        adminClient.close();
//        return adminClient;
//    }
}
