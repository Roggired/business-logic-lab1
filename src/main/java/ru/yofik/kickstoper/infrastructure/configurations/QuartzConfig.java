package ru.yofik.kickstoper.infrastructure.configurations;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import ru.yofik.kickstoper.context.project.service.ProjectDeleteFinishedJob;
import ru.yofik.kickstoper.context.project.service.ProjectDiscoverJob;

import javax.sql.DataSource;

@Configuration
public class QuartzConfig {
    @Bean(name = "projectDiscoverJobDetails")
    public JobDetailFactoryBean projectDiscoverJobDetails() {
        final JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(ProjectDiscoverJob.class);
        factoryBean.setName(ProjectDiscoverJob.JOB_NAME);
        factoryBean.setGroup("discoverers");
        factoryBean.setDurability(true);

        return factoryBean;
    }

    @Bean(name = "projectDeleteFinishedJobDetails")
    public JobDetailFactoryBean projectDeleteFinishedJobDetails() {
        final JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(ProjectDeleteFinishedJob.class);
        factoryBean.setName(ProjectDeleteFinishedJob.JOB_NAME);
        factoryBean.setGroup("discoverers");
        factoryBean.setDurability(true);

        return factoryBean;
    }

    @Bean
    @ConfigurationProperties("datasource.quartz.hikari")
    public HikariConfig quartzHikariConfig() {
        return new HikariConfig();
    }

    @Bean("quartzDataSource")
    @QuartzDataSource
    public DataSource quartzDataSource(HikariConfig quartzHikariConfig) {
        return new HikariDataSource(quartzHikariConfig);
    }
}
