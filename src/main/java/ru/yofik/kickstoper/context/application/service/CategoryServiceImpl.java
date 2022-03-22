package ru.yofik.kickstoper.context.application.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yofik.kickstoper.api.exceptions.RequestedElementNotExistException;
import ru.yofik.kickstoper.context.application.entity.Category;
import ru.yofik.kickstoper.context.application.repository.CategoryRepository;

import java.util.List;

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
        return categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn(() -> "Category with id: " + id + " does not exist");
                    return new RequestedElementNotExistException("Category with id: " +  id + " doesn't exist");
                });
    }

    @Override
    public List<Category> getAll() {
        List<Category> categories = categoryRepository.findAll();
        log.info("All categories has been obtained");
        return categories;
    }
}
