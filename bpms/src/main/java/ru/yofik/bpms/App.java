package ru.yofik.bpms;

import lombok.extern.log4j.Log4j2;
import ru.yofik.bpms.cli.CLI;
import ru.yofik.bpms.camunda.CamundaConfig;

import static ru.yofik.bpms.utils.LogColorizer.yellow;

@Log4j2
public class App {
    private static final String DEFAULT_CONFIG_FILE = "camunda-config.json";
    private static final String DEFAULT_DIAGRAMS_DIRECTORY = "diagrams";


    public static void main(String[] args) {
        var configFilename = System.getenv("CAMUNDA_CONFIG");
        if (configFilename == null || configFilename.isBlank()) {
            log.warn(yellow("CAMUNDA_CONFIG") + " is unset. Switched to default config file: " + DEFAULT_CONFIG_FILE);
            configFilename = DEFAULT_CONFIG_FILE;
        }

        var diagramsDirectory = System.getenv("DIAGRAMS_DIRECTORY");
        if (diagramsDirectory == null || diagramsDirectory.isBlank()) {
            log.warn(yellow("DIAGRAMS_DIRECTORY") + " is unset. Switched to default location: " + DEFAULT_DIAGRAMS_DIRECTORY);
            diagramsDirectory = DEFAULT_DIAGRAMS_DIRECTORY;
        }

        var camundaConfig = new CamundaConfig(configFilename, diagramsDirectory);
        var camundaFacade = camundaConfig.configureCamunda();
        var cli = new CLI(camundaFacade);
        cli.start();

        log.debug("Stopping Camunda ProcessEngine...");
        camundaFacade.stop();
        log.info("Shutdown completed");
    }
}
