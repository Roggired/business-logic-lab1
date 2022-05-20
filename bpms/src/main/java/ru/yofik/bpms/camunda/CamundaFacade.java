package ru.yofik.bpms.camunda;

import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import ru.yofik.bpms.storage.ProcessDefinitionDao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

import static ru.yofik.bpms.utils.LogColorizer.green;
import static ru.yofik.bpms.utils.LogColorizer.red;

@Log4j2
public final class CamundaFacade {
    private final ProcessEngine processEngine;
    private final CamundaContext camundaContext;
    private final ProcessDefinitionDao processDefinitionDao;

    public CamundaFacade(ProcessEngine processEngine, CamundaContext camundaContext, ProcessDefinitionDao processDefinitionDao) {
        this.processEngine = processEngine;
        this.camundaContext = camundaContext;
        this.processDefinitionDao = processDefinitionDao;
    }


    public void startProcessByDefinitionId(String id) {
        var processInstance = processEngine.getRuntimeService().startProcessInstanceById(id);
        camundaContext.addActiveProcess(processInstance);
    }

    public void deployResourceName(String deployResourceName) {
        try {
            log.debug("Started deploying process: " + deployResourceName +
                    " diagrams directory: " + Path.of(camundaContext.getDiagramsDirectory()).toAbsolutePath());
            var deployment = processEngine.getRepositoryService().createDeployment()
                    .addInputStream(
                            deployResourceName,
                            new FileInputStream(
                                    Path.of(
                                            camundaContext.getDiagramsDirectory(),
                                            deployResourceName
                                    ).toString()
                            )
                    )
                    .deployWithResult();

            log.info("Deployed id: {}, name: {}", deployment.getId(), deployment.getName() + " - " + green("OK"));
            log.info("Process definitions: " + deployment.getDeployedProcessDefinitions());
            camundaContext.addDeployment(deployment);
        } catch (RuntimeException e) {
            log.error(red("Deploy FAIL") + " process: " + deployResourceName + " reason: Can't deploy process: ", e);
        } catch (FileNotFoundException e) {
            log.error(red("Deploy FAIL") + " process: " + deployResourceName +
                    " reason: Can't find a resource file for path: " + Path.of(deployResourceName).toAbsolutePath());
        }
    }

    public void startAllProcesses() {
        camundaContext.getDeployments().forEach(deploymentWithDefinition ->
                deploymentWithDefinition.getDeployedProcessDefinitions().forEach(
                        processDefinition -> camundaContext.addActiveProcess(
                                processEngine.getRuntimeService()
                                        .startProcessInstanceById(
                                                processDefinition.getId()
                                        )
                        )
                )
        );
    }

    public List<CamundaProcessInfo> getAllDeployedProcesses() {
        return processDefinitionDao.getAll();
    }

    public void deleteDeployment(String id) {
        processEngine.getRepositoryService().deleteDeployment(id, true);
    }

    public void deleteAllDeployments() {
        getAllDeployedProcesses()
                .stream()
                .map(camundaProcessInfo -> camundaProcessInfo.deploymentId)
                .distinct()
                .forEach(this::deleteDeployment);
    }

    public void stop() {
        processEngine.close();
    }
}
