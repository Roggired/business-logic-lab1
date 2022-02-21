package ru.yofik.kickstoper.storage.sql.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yofik.kickstoper.domain.entity.application.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Application findByProjectName(String projectName);
}
