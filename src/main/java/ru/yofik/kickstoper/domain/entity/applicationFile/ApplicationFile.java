package ru.yofik.kickstoper.domain.entity.applicationFile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationFile {
    private String filename;
    private byte[] data;
}
