package ru.yofik.bpms.cli;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * argumentTypes.size() MUST BE equal to the number of argument placeholders in a command
 */
@AllArgsConstructor
enum Command {
    DEPLOY_PROCESS_RESOURCE(
            "deployProcess",
            List.of(String.class),
            DeployProcessHandler.class,
            "deployProcess RESOURCE_NAME - deploys a diagrom from DIAGRAMS_DIRECTORY"
    ),
    LIST_ALL_DEPLOYMENTS(
            "listDeployments",
            Collections.emptyList(),
            ListDeploymentsHandler.class,
            "listDeployments - list all deployments"
    ),
    DELETE_DEPLOYMENT(
            "deleteDeployment",
            List.of(String.class),
            DeleteDeploymentHandler.class,
            "deleteDeployment DEPLOYMENT_ID - delete deployment by id"
    ),
    DELETE_ALL_DEPLOYMENTS(
            "deleteAllDeployments",
            Collections.emptyList(),
            DeleteAllDeploymentHandler.class,
            "deleteAllDeployments - delete all deployments"
    ),
    START_PROCESS_BY_ID(
            "start",
            List.of(String.class),
            StartProcessByIdHandler.class,
            "start PROCESS_ID - starts a business process with given id. The process MUST BE deployed first"
    ),
    START_ALL_PROCESSES(
            "startAll",
            Collections.emptyList(),
            StartAllProcessesHandler.class,
            "startAll - starts all deployed business process"
    ),
    HELP(
            "help",
            Collections.emptyList(),
            HelpHandler.class,
            "help - list commands"
    ),
    SHUTDOWN(
            "shutdown",
            Collections.emptyList(),
            ShutdownHandler.class,
            "shutdown - exit"
    )
    ;

    public static Optional<Command> fromString(String commandName) {
        return Arrays.stream(values())
                .filter(command -> command.commandName.equals(commandName))
                .findFirst();
    }

    public final String commandName;
    public final List<Class<?>> argumentTypes;
    public final Class<? extends CommandHandler> handler;
    public final String help;
}

