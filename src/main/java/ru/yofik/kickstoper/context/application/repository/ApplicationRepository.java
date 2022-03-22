package ru.yofik.kickstoper.context.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yofik.kickstoper.context.application.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Application findByProjectName(String projectName);
}
