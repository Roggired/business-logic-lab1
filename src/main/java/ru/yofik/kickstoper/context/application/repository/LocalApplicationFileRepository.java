package ru.yofik.kickstoper.context.application.repository;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Repository;
import ru.yofik.kickstoper.api.exceptions.InternalServerException;
import ru.yofik.kickstoper.context.application.entity.ApplicationFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
@Log4j2
public class LocalApplicationFileRepository implements ApplicationFileRepository {
    @Value("${local.files.directory}")
    private String pathToDirectory;

    @Value("${local.files.maxFileSize}")
    private int maxFileSize;

    @Autowired
    private TaskExecutor taskExecutor;


    @Override
    public void save(ApplicationFile applicationFile) {
        Path directory = getDirectoryPath();
        log.info(() -> "Path to uploaded files directory has been obtained");

        Path filePath = directory.resolve(applicationFile.getFilename());
        log.info(() -> "Target path for file: " + applicationFile.getFilename() + " is resolved");

        deleteIfExists(filePath);

        taskExecutor.execute(() -> {
            saveToFile(applicationFile.getData(), filePath);
            log.info(() -> "File has been saved to: " + filePath);
        });
    }

    @Override
    public void delete(String filename) {
        Path directory = getDirectoryPath();
        log.info(() -> "Path to uploaded files directory has been obtained");

        Path filePath = directory.resolve(filename);
        log.info(() -> "Target path for file: " + filePath + " is resolved");

        deleteIfExists(filePath);
    }

    @Override
    public byte[] get(String filename) {
        Path directory = getDirectoryPath();
        log.info(() -> "Path to uploaded files directory has been obtained");

        Path filePath = directory.resolve(filename);
        log.info(() -> "Target path for file: " + filePath + " is resolved");

        if (Files.notExists(filePath)) {
            log.info(() -> "There is no such file: " + filePath);
            return new byte[0];
        }

        byte[] data = read(filePath);
        log.info(() -> "File: " + filename + " has been read");
        return data;
    }


    private byte[] read(Path filePath) {
        try(BufferedInputStream input = new BufferedInputStream(new FileInputStream(filePath.toFile()))) {
            byte[] bytes = new byte[maxFileSize * 1024 * 1024];
            int readBytesAmount = input.read(bytes, 0, bytes.length);
            byte[] data = new byte[readBytesAmount];
            System.arraycopy(bytes, 0, data, 0, data.length);
            return data;
        } catch (IOException e) {
            log.fatal(() -> "Exception during reading a file by path: " + filePath);
            throw new InternalServerException();
        }
    }

    private void deleteIfExists(Path path) {
        try {
            if (Files.deleteIfExists(path)) {
                log.info(() -> "File by path: " + path + " exists. Existed file has been deleted");
            }
        } catch (IOException e) {
            log.fatal(() -> "Cannot delete existed file by path: " +  path, e);
            throw new InternalServerException();
        }
    }

    private void saveToFile(byte[] data, Path filePath) {
        try(BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(filePath.toFile()))) {
            output.write(data);
            output.flush();
        } catch (IOException e) {
            log.fatal(() -> "Exception during saving a file by path: " + filePath, e);
        }
    }

    private Path getDirectoryPath() {
        try {
            if (pathToDirectory == null || pathToDirectory.isEmpty()) {
                log.fatal(() -> "Path to uploaded files directory is unset or blank");
                throw new InternalServerException();
            }

            Path path = Paths.get(pathToDirectory).toRealPath();
            if (!Files.isDirectory(path) || !Files.isReadable(path) || !Files.isWritable(path)) {
                log.fatal(() -> "Path to uploaded files directory points to either not a directory, or not readable directory, or not writable directory");
                throw new InternalServerException();
            }

            return path;
        } catch (IOException e) {
            log.fatal(() -> "Cannot locate directory for uploaded files", e);
            throw new InternalServerException();
        }
    }
}
