package ru.yofik.kickstoper.context.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yofik.kickstoper.context.project.model.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findAllByProjectNameContains(String name);
    List<Project> findAllByCategoryId(int categoryId);
    List<Project> findAllByCategoryIdAndSubcategoryId(int categoryId, int subcategoryId);
}
