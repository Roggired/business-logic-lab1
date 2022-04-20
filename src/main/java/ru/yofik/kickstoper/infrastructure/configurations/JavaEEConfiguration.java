package ru.yofik.kickstoper.infrastructure.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.support.JmxUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class JavaEEConfiguration {
    @Value("${datasource.db.driver-class-name}")
    private String driverName;
    @Value("${datasource.db.jndi-name}")
    private String jndiDataSourceName;


    @Bean(name = "transactionManager")
    public JtaTransactionManager provideTransactionManager(){
        return new JtaTransactionManager();
    }

    @Bean
    @Primary
    public DataSource dbDataSource(ApplicationContext context) {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        DataSource dataSource = dataSourceLookup.getDataSource(jndiDataSourceName);
        excludeMBeanIfNecessary(dataSource, "dataSource", context);
        return dataSource;
    }

    private void excludeMBeanIfNecessary(Object candidate, String beanName, ApplicationContext context) {
        for (MBeanExporter mbeanExporter : context.getBeansOfType(MBeanExporter.class).values()) {
            if (JmxUtils.isMBean(candidate.getClass())) {
                mbeanExporter.addExcludedBean(beanName);
            }
        }
    }
}
