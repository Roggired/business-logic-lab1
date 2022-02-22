package ru.yofik.kickstoper.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yofik.kickstoper.domain.entity.subcategory.Subcategory;
import ru.yofik.kickstoper.domain.service.subcategory.SubcategoryService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/subcategories",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class SubcategoryResource {
    @Autowired
    private SubcategoryService subcategoryService;


    @GetMapping("")
    public List<Subcategory> getAllSubcategories() {
        return subcategoryService.getAll();
    }

    @GetMapping("/{id}")
    public Subcategory getSubcategory(@PathVariable int id) {
        return subcategoryService.getById(id);
    }
}
