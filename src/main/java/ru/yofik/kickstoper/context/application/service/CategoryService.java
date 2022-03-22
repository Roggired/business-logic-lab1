package ru.yofik.kickstoper.context.application.service;

import org.springframework.stereotype.Service;
import ru.yofik.kickstoper.context.application.entity.Category;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public interface CategoryService {
    boolean isExist(int id);
    @NotNull Category getById(int id);
    @NotNull List<Category> getAll();
}
