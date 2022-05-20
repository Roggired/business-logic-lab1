package ru.yofik.bpms.cli;

import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public final class ListDeploymentsHandler implements CommandHandler{
    @Override
    public void handle(CommandInterpreter commandInterpreter, List<Object> arguments) {
        var deployedProcesses = commandInterpreter.getCamundaFacade().getAllDeployedProcesses();
        commandInterpreter.println("Deployed processes:");
        deployedProcesses.forEach(processInfo -> commandInterpreter.println(
                "ProcessID: " + processInfo.id + " version: " + processInfo.version + " DeploymentID: " + processInfo.deploymentId
        ));
        commandInterpreter.println("Total: " + deployedProcesses.size());
    }
}
