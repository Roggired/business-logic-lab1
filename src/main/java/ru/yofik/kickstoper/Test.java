package ru.yofik.kickstoper;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.application.*;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;

import javax.sql.DataSource;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        var engine = BpmPlatform.getDefaultProcessEngine(); // null
        var service = BpmPlatform.getProcessEngineService();
        System.out.println("Process engine names: " + service.getProcessEngines().stream().map(ProcessEngine::getName).collect(Collectors.toList()));

        var processEngine = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
                .setProcessEngineName("test-engine")
                .setAuthorizationEnabled(false)
                .setJdbcUrl("jdbc:postgresql://localhost/postgres")
                .setJdbcUsername("postgres")
                .setJdbcPassword("postgres")
                .buildProcessEngine();


        processEngine.getManagementService().registerProcessApplication();
    }
}
