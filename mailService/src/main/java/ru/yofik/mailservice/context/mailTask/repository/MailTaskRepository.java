package ru.yofik.mailservice.context.mailTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yofik.mailservice.context.mailTask.model.MailTask;

import java.util.Optional;

@Repository
public interface MailTaskRepository extends JpaRepository<MailTask, Integer> {
    Optional<MailTask> findById(int id);
}
