package ru.yofik.kickstoper.context.application.repository;

import org.springframework.stereotype.Repository;
import ru.yofik.kickstoper.context.application.entity.ApplicationFile;

@Repository
public interface ApplicationFileRepository {
    void save(ApplicationFile applicationFile);
    void delete(String filename);
    byte[] get(String filename);
}
