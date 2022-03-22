package ru.yofik.kickstoper.context.application.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationFile {
    private String filename;
    private byte[] data;
}
