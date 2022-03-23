package ru.yofik.kickstoper.context.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yofik.kickstoper.context.application.model.Subcategory;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {
}
