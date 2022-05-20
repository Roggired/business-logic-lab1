package ru.yofik.bpms.cli;

import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
final class StartProcessByIdHandler implements CommandHandler {
    @Override
    public void handle(CommandInterpreter commandInterpreter, List<Object> arguments) {
        var processId = (String) arguments.get(0);
        commandInterpreter.getCamundaFacade().startProcessByDefinitionId(processId);
    }
}
