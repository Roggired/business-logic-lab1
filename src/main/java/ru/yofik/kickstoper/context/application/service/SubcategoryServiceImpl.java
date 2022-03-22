package ru.yofik.kickstoper.context.application.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yofik.kickstoper.context.application.exception.RequestedElementNotExistException;
import ru.yofik.kickstoper.context.application.entity.Subcategory;
import ru.yofik.kickstoper.context.application.repository.SubcategoryRepository;

import java.util.List;

@Service
@Log4j2
public class SubcategoryServiceImpl implements SubcategoryService {
    @Autowired
    private SubcategoryRepository subcategoryRepository;


    @Override
    public boolean isExist(int id) {
        return subcategoryRepository.existsById(id);
    }

    @Override
    public Subcategory getById(int id) {
        return subcategoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn(() -> "Subcategory with id: " +  id + " does not exist");
                    return new RequestedElementNotExistException("Subcategory with id: " + id + " doesn't exist");
                });
    }

    @Override
    public List<Subcategory> getAll() {
        List<Subcategory> subcategories = subcategoryRepository.findAll();
        log.info("All subcategories has been obtained");
        return subcategories;
    }
}
