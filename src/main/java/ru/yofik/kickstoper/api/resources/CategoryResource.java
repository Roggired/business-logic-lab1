package ru.yofik.kickstoper.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yofik.kickstoper.domain.entity.category.Category;
import ru.yofik.kickstoper.domain.service.category.CategoryService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/categories",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class CategoryResource {
    @Autowired
    private CategoryService categoryService;


    @GetMapping("")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable int id) {
        return categoryService.getById(id);
    }
}
