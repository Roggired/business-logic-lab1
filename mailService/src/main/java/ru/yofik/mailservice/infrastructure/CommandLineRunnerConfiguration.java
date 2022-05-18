package ru.yofik.mailservice.infrastructure;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yofik.mailservice.context.mailTask.service.KafkaMessageListener;

@Configuration
@Log
public class CommandLineRunnerConfiguration {
    @Bean
    public CommandLineRunner produceCommandLineRunner(@Autowired KafkaMessageListener kafkaMessageListener) {
        return args -> kafkaMessageListener.init();
    }
}
