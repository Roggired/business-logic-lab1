package ru.yofik.kickstoper.domain.service.subcategory;

import org.springframework.stereotype.Service;
import ru.yofik.kickstoper.domain.entity.subcategory.Subcategory;

import javax.validation.constraints.NotNull;

@Service
public interface SubcategoryService {
    boolean isExist(int id);
    @NotNull Subcategory getById(int id);
}
