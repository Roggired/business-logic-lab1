package ru.yofik.bpms.cli;

import ru.yofik.bpms.camunda.CamundaFacade;

import java.util.List;
import java.util.Scanner;

import static ru.yofik.bpms.utils.LogColorizer.yellow;

public final class CLI {
    private final CamundaFacade camundaFacade;
    private final CommandInterpreter commandInterpreter;
    private boolean isRun = false;


    public CLI(CamundaFacade camundaFacade) {
        this.camundaFacade = camundaFacade;
        this.commandInterpreter = new CommandInterpreter(
                this,
                List.of(Command.values())
        );
    }

    public void start() {
        println("Kickstoper BPMS CLI started");
        println("Type 'help' for list of commands");

        isRun = true;

        try(var scanner = new Scanner(System.in)) {
            while (isRun) {
                print(yellow(">"));
                var input = scanner.nextLine();
                commandInterpreter.interpret(input);
            }
        }
    }

    public void stop() {
        isRun = false;
    }

    CamundaFacade getCamundaFacade() {
        return camundaFacade;
    }

    void println(String text) {
        System.out.println(text);
    }

    void println() {
        System.out.println();
    }

    void print(String text) {
        System.out.print(text);
    }
}
