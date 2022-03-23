package ru.yofik.kickstoper.infrustructure.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class FileManagementConfiguration {
    @Value("${local.files.maxFileSize}")
    private int maxFileSize;

    @Bean
    public MultipartConfigElement provideMultipartConfig() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(maxFileSize));
        factory.setMaxRequestSize(DataSize.ofMegabytes(maxFileSize));
        return factory.createMultipartConfig();
    }
}
