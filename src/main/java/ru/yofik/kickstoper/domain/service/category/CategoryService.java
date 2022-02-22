package ru.yofik.kickstoper.domain.service.category;

import org.springframework.stereotype.Service;
import ru.yofik.kickstoper.domain.entity.category.Category;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public interface CategoryService {
    boolean isExist(int id);
    @NotNull Category getById(int id);
    @NotNull List<Category> getAll();
}
