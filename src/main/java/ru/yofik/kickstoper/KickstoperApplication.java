package ru.yofik.kickstoper;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Log4j2
@SpringBootApplication
public class KickstoperApplication {
    public static void main(String[] args) {
        SpringApplication.run(KickstoperApplication.class, args);
    }
}

