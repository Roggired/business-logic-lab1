package ru.yofik.bpms.storage;

import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import ru.yofik.bpms.camunda.CamundaConfig;
import ru.yofik.bpms.camunda.CamundaProcessInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public final class ProcessDefinitionDao {
    private final CamundaConfig.DatasourceConfig datasourceConfig;

    public ProcessDefinitionDao(CamundaConfig.DatasourceConfig datasourceConfig) {
        this.datasourceConfig = datasourceConfig;
    }

    public List<CamundaProcessInfo> getAll() {
        try(var connection = createConnection()) {
            var deploymentPreparedStatement = connection.prepareStatement(
                    "SELECT id_ FROM act_re_deployment"
            );
            var resultSet = deploymentPreparedStatement.executeQuery();
            var deploymentsIds = new ArrayList<String>();

            while (resultSet.next()) {
                deploymentsIds.add(resultSet.getString("id_"));
            }

            if (deploymentsIds.isEmpty()) {
                return Collections.emptyList();
            }

            var processDefinitionStatement = connection.createStatement();
            resultSet = processDefinitionStatement.executeQuery(
                            "SELECT id_, version_, deployment_id_ FROM act_re_procdef WHERE deployment_id_ IN (" +
                                    deploymentsIds.stream()
                                            .map(id -> "'" + id + "'")
                                            .collect(Collectors.joining(","))
                            + ")"
            );
            var processInfos = new ArrayList<CamundaProcessInfo>();
            while (resultSet.next()) {
                var processId = resultSet.getString("id_");
                var version = resultSet.getInt("version_");
                var deploymentId = resultSet.getString("deployment_id_");
                processInfos.add(
                        new CamundaProcessInfo(
                                processId,
                                version,
                                deploymentId
                        )
                );
            }

            return processInfos;
        } catch (SQLException e) {
            throw new StorageException("Can't close connection to db", e);
        }
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(
                    datasourceConfig.jdbcUrl,
                    datasourceConfig.username,
                    datasourceConfig.password
            );
        } catch (SQLException e) {
            throw new StorageException("Can't connect to db", e);
        }
    }
}
