package ru.yofik.kickstoper.context.application.service;

import org.springframework.stereotype.Service;
import ru.yofik.kickstoper.context.application.model.Subcategory;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public interface SubcategoryService {
    boolean isExist(int id);
    @NotNull Subcategory getById(int id);
    @NotNull List<Subcategory> getAll();
}
