package ru.yofik.kickstoper.storage.local;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Repository;
import ru.yofik.kickstoper.api.exceptions.InternalServerException;
import ru.yofik.kickstoper.domain.entity.applicationFile.ApplicationFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
@Log4j2
public class LocalApplicationFileRepository implements ApplicationFileRepository {
    @Value("${local.files.directory}")
    private String pathToDirectory;

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
            if (pathToDirectory == null || pathToDirectory.isBlank()) {
                log.fatal(() -> "Path to uploaded files directory is unset or blank");
                throw new InternalServerException();
            }

            Path path = Path.of(pathToDirectory).toRealPath();
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
