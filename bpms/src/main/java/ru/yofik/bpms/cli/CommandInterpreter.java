package ru.yofik.bpms.cli;

import lombok.extern.log4j.Log4j2;
import ru.yofik.bpms.camunda.CamundaFacade;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static ru.yofik.bpms.utils.LogColorizer.*;

@Log4j2
final class CommandInterpreter {
    private static final Map<Class<?>, Parser> PARSERS = new HashMap<>() {{
        put(Integer.class, CommandInterpreter::parseInteger);
        put(String.class, CommandInterpreter::parseString);
    }};


    private final CLI cli;
    private final List<Command> commands;

    public CommandInterpreter(CLI cli, List<Command> commands) {
        this.cli = cli;
        this.commands = commands;
    }


    public void interpret(String input) {
        var commandOptional = parseCommand(input);
        if (commandOptional.isEmpty()) {
            println(red("Invalid command") + "Wrong command name");
            println(yellow("Type help to get full command list"));
            println();
            return;
        }

        var command = commandOptional.get();
        List<Object> arguments;

        try {
            if (command.argumentTypes.size() > 0) {
                arguments = parseArguments(command, input);
            } else {
                arguments = Collections.emptyList();
            }
        } catch (ParseException e) {
            println(red("Invalid command") + e.getMessage());
            println(yellow("Help:") + command.help);
            println();
            return;
        }

        var handler = instantiateHandler(command.handler);
        handler.handle(this, arguments);
        println(green("OK"));
        println();
    }

    public void println(String text) {
        cli.println(text);
    }

    public void println() {
        cli.println();
    }

    public void print(String text) {
        cli.print(text);
    }

    public List<Command> getSupportedCommand() {
        return commands;
    }

    public CamundaFacade getCamundaFacade() {
        return cli.getCamundaFacade();
    }

    public void sendStopToCli() {
        cli.stop();
    }

    private Optional<Command> parseCommand(String input) {
        var firstSpaceIndex = input.indexOf(" ");
        var commandAsString = input;
        if (firstSpaceIndex > -1) {
            commandAsString = input.substring(0, firstSpaceIndex);
        }
        return Command.fromString(commandAsString);
    }

    private List<Object> parseArguments(Command command, String input) {
        var parts = input.split(" ");
        var arguments = new ArrayList<>();

        if (parts.length - 1 < command.argumentTypes.size()) {
            throw new ParseException("Too few arguments");
        }

        if (parts.length - 1 > command.argumentTypes.size()) {
            throw new ParseException("Too many arguments");
        }

        for (int i = 1; i < parts.length; i++) {
            var argumentType = command.argumentTypes.get(i - 1);
            var parser = PARSERS.get(argumentType);

            if (parser == null) {
                throw new ParseException("Invalid argument type index: " + (i - 1));
            }

            arguments.add(parser.parse(parts[i], i - 1));
        }

        return arguments;
    }

    private static CommandHandler instantiateHandler(Class<? extends CommandHandler> handler) {
        try {
            var constructor = handler.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.fatal("CAN'T INSTANTIATE HANDLER OF CLASS: " + handler.getName());
            throw new RuntimeException(e);
        }
    }

    private static Object parseInteger(String part, int i) {
        try {
            return Integer.parseInt(part);
        } catch (NumberFormatException e) {
            throw new ParseException("Argument with index: " + i + " must be an integer");
        }
    }

    private static Object parseString(String part, int i) {
        return part;
    }

    @FunctionalInterface
    private interface Parser {
        Object parse(String part, int i);
    }

    private static class ParseException extends RuntimeException {
        public ParseException(String message) {
            super(message);
        }
    }
}
