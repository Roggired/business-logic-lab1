package ru.yofik.kickstoper.storage.sql.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yofik.kickstoper.domain.entity.category.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
