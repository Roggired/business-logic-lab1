package ru.yofik.bpms.utils;

import lombok.AllArgsConstructor;

public final class LogColorizer {
    private static final String RESET = "\u001b[0m";

    private static String colorize(String text, Color color) {
        return color.code + text + RESET;
    }

    public static String red(String text) {
        return colorize(text, Color.RED);
    }

    public static String green(String text) {
        return colorize(text, Color.GREEN);
    }

    public static String yellow(String text) {
        return colorize(text, Color.YELLOW);
    }

    @AllArgsConstructor
    private enum Color {
        RED("\u001b[31m"),
        GREEN("\u001b[32m"),
        YELLOW("\u001b[33m"),
        ;

        private final String code;
    }
}
