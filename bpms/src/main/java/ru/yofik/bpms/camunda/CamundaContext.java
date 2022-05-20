package ru.yofik.bpms.camunda;

import org.camunda.bpm.engine.repository.DeploymentWithDefinitions;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import java.util.ArrayList;
import java.util.List;

public final class CamundaContext {
    private final String diagramsDirectory;
    private final List<String> processResourceNames;
    private final List<DeploymentWithDefinitions> deployments;
    private final List<ProcessInstance> processInstances;


    public CamundaContext(
            String diagramsDirectory,
            List<String> processResourceNames,
            List<DeploymentWithDefinitions> deployments
    ) {
        this.diagramsDirectory = diagramsDirectory;
        this.processResourceNames = processResourceNames;
        this.deployments = deployments;
        this.processInstances = new ArrayList<>();
    }


    public String getDiagramsDirectory() {
        return diagramsDirectory;
    }

    public void addDeployment(DeploymentWithDefinitions deployment) {
        deployments.add(deployment);
    }

    public List<DeploymentWithDefinitions> getDeployments() {
        return new ArrayList<>(deployments);
    }

    public void addActiveProcess(ProcessInstance processInstance) {
        processInstances.add(processInstance);
    }

    public List<ProcessInstance> getProcessInstances() {
        return new ArrayList<>(processInstances);
    }

    public List<String> getProcessResourceNames() {
        return new ArrayList<>(processResourceNames);
    }

    public boolean containsProcessResourceName(String processResourceName) {
        return processResourceNames.contains(processResourceName);
    }
}
