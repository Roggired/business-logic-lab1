package ru.yofik.bpms.cli;

import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class ShutdownHandler implements CommandHandler {
    @Override
    public void handle(CommandInterpreter commandInterpreter, List<Object> arguments) {
        commandInterpreter.sendStopToCli();
    }
}
