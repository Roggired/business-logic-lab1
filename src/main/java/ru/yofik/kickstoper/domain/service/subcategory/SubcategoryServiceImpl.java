package ru.yofik.kickstoper.domain.service.subcategory;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yofik.kickstoper.api.exceptions.RequestedElementNotExistException;
import ru.yofik.kickstoper.domain.entity.subcategory.Subcategory;
import ru.yofik.kickstoper.storage.sql.subcategory.SubcategoryRepository;

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
        if (!isExist(id)) {
            log.warn(() -> "Subcategory with id: " +  id + " does not exist");
            throw new RequestedElementNotExistException("Subcategory with id: " + id + " doesn't exist");
        }

        return subcategoryRepository.getById(id);
    }
}
