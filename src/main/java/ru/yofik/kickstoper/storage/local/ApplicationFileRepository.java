package ru.yofik.kickstoper.storage.local;

import org.springframework.stereotype.Repository;
import ru.yofik.kickstoper.domain.entity.applicationFile.ApplicationFile;

@Repository
public interface ApplicationFileRepository {
    void save(ApplicationFile applicationFile);
    void delete(String filename);
}
