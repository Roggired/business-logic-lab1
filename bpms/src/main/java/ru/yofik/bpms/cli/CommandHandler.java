package ru.yofik.bpms.cli;

import java.util.List;

interface CommandHandler {
    void handle(CommandInterpreter commandInterpreter, List<Object> arguments);
}
