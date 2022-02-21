package ru.yofik.kickstoper.domain.service.category;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yofik.kickstoper.api.exceptions.RequestedElementNotExistException;
import ru.yofik.kickstoper.domain.entity.category.Category;
import ru.yofik.kickstoper.storage.sql.category.CategoryRepository;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public boolean isExist(int id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public Category getById(int id) {
        if (!isExist(id)) {
            log.warn(() -> "Category with id: " + id + " doesn't exist");
            throw new RequestedElementNotExistException("Category with id: " +  id + " doesn't exist");
        }

        return categoryRepository.getById(id);
    }
}
