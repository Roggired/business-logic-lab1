package ru.yofik.kickstoper.context.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yofik.kickstoper.context.mail.model.MailTask;

@Repository
public interface MailTaskRepository extends JpaRepository<MailTask, Integer> {
}
