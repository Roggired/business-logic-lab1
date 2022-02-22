package ru.yofik.kickstoper.storage.sql.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yofik.kickstoper.domain.entity.application.Application;
import ru.yofik.kickstoper.domain.entity.application.ApplicationStatus;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Application findByProjectName(String projectName);

    @Transactional
    @Modifying
    @Query("UPDATE Application a SET a.applicationStatus = :status WHERE a.id = :id")
    int updateStatus(@Param("id") int id, @Param("status") ApplicationStatus status);
}
