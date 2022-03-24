package ru.yofik.kickstoper.infrastructure.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
public class JavaEEConfiguration {
    @Bean(name = "transactionManager")
    public JtaTransactionManager provideTransactionManager(){
        return new JtaTransactionManager();
    }
}