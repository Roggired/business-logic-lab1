package ru.yofik.kickstoper.domain.service.category;

import org.springframework.stereotype.Service;
import ru.yofik.kickstoper.domain.entity.category.Category;

import javax.validation.constraints.NotNull;

@Service
public interface CategoryService {
    boolean isExist(int id);
    @NotNull Category getById(int id);
}
