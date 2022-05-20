package ru.yofik.bpms.camunda;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import ru.yofik.bpms.storage.ProcessDefinitionDao;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.yofik.bpms.utils.LogColorizer.green;

@Log4j2
public final class CamundaConfig {
    private final String configFilename;
    private final String diagramsDirectory;
    private List<String> processes;

    public CamundaConfig(String configFilename, String diagramsDirectory) {
        this.configFilename = configFilename;
        this.diagramsDirectory = diagramsDirectory;
    }

    public CamundaFacade configureCamunda() {
        log.debug("Started configuring Camunda Embedded ProcessEngine...");
        log.debug("Config file name: " + configFilename);

        var gson = new GsonBuilder()
                .registerTypeAdapter(Configuration.class, new ConfigurationDeserializer())
                .registerTypeAdapter(DatasourceConfig.class, new DatasourceConfigDeserializer())
                .create();

        var configAsJson = readConfigFile();
        log.debug("Configuration file has been read - " + green("OK"));
        var config = gson.fromJson(configAsJson, Configuration.class);
        log.debug("JSON Configuration has been parsed - " + green("OK"));

        var processEngineConfig =
                ProcessEngineConfiguration
                        .createStandaloneProcessEngineConfiguration()
                        .setProcessEngineName("camunda-bpms-engine");

        if (config.datasource != null) {
            processEngineConfig = processEngineConfig
                    .setJdbcUrl(config.datasource.jdbcUrl)
                    .setJdbcUsername(config.datasource.username)
                    .setJdbcPassword(config.datasource.password)
                    .setJdbcDriver(config.datasource.driver)
                    .setDatabaseSchemaUpdate(config.datasource.ddlAuto);

            log.debug("Datasource config has been applied: " + config.datasource);
        }

        var processEngine = processEngineConfig.buildProcessEngine();
        var camundaContext = new CamundaContext(diagramsDirectory, processes, new ArrayList<>());

        log.info("Camunda ProcessEngine has been configured");
        return new CamundaFacade(processEngine, camundaContext, new ProcessDefinitionDao(config.datasource));
    }

    private String readConfigFile() {
        var file = Path.of(configFilename).toAbsolutePath();
        if (!Files.exists(file)) {
            throw new ConfigException("Config file cannot be found: " + file);
        }

        try(var stream = Files.lines(file, StandardCharsets.UTF_8)) {
            return stream.collect(Collectors.joining());
        } catch (IOException e) {
            throw new ConfigException("Unexpected exception during reading the file: " + file, e);
        }
    }

    public static class ConfigurationDeserializer implements JsonDeserializer<Configuration> {
        @Override
        public Configuration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                var configJsonObject = json.getAsJsonObject();
                var datasourceJsonObject = configJsonObject.getAsJsonObject("datasource");
                var processesJsonArray = configJsonObject.getAsJsonArray("processes");
                var formsJsonArray = configJsonObject.getAsJsonArray("forms");

                var datasourceConfig = (DatasourceConfig) context.deserialize(datasourceJsonObject, DatasourceConfig.class);
                var processes = (List<String>) context.deserialize(processesJsonArray, new TypeToken<List<String>>(){}.getType());
                var forms = (List<String>) context.deserialize(formsJsonArray, new TypeToken<List<String>>(){}.getType());

                return new Configuration(
                        datasourceConfig,
                        processes,
                        forms
                );
            } catch (RuntimeException e) {
                throw new RuntimeException("Invalid config", e);
            }
        }
    }

    public static class DatasourceConfigDeserializer implements JsonDeserializer<DatasourceConfig> {
        @Override
        public DatasourceConfig deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                var datasourceJsonObject = json.getAsJsonObject();
                var jdbcUrl = datasourceJsonObject.get("jdbcUrl").getAsString();
                var username = datasourceJsonObject.get("username").getAsString();
                var password = datasourceJsonObject.get("password").getAsString();
                var driver = datasourceJsonObject.get("driver").getAsString();
                var ddlAuto = datasourceJsonObject.get("ddl-auto").getAsString();

                return new DatasourceConfig(
                        jdbcUrl,
                        username,
                        password,
                        driver,
                        ddlAuto
                );
            } catch (RuntimeException e) {
                throw new RuntimeException("Invalid datasource config", e);
            }
        }
    }

    @AllArgsConstructor
    @ToString
    public static class Configuration {
        public final DatasourceConfig datasource;
        public final List<String> processes;
        public final List<String> forms;
    }

    @AllArgsConstructor
    @ToString
    public static class DatasourceConfig {
        public final String jdbcUrl;
        public final String username;
        public final String password;
        public final String driver;
        public final String ddlAuto;
    }
}
