package ru.yofik.kickstoper.storage.sql.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yofik.kickstoper.domain.entity.application.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}