package ru.yofik.kickstoper.context.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationFile {
    private String filename;
    private byte[] data;
}
