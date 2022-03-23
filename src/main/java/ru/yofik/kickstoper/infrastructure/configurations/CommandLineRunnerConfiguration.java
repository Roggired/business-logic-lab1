package ru.yofik.kickstoper.infrastructure.configurations;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yofik.kickstoper.context.application.model.Category;
import ru.yofik.kickstoper.context.application.model.Subcategory;
import ru.yofik.kickstoper.context.application.repository.CategoryRepository;
import ru.yofik.kickstoper.context.application.repository.SubcategoryRepository;

import java.util.Arrays;
import java.util.List;

@Configuration
@Log
public class CommandLineRunnerConfiguration {
    @Bean
    public CommandLineRunner produceCommandLineRunner(
            @Autowired CategoryRepository categoryRepository,
            @Autowired SubcategoryRepository subcategoryRepository
    ) {
        return args -> {
            List<Category> categories = Arrays.asList(
                    new Category(
                            0,
                            "Video Game"
                    )
            );
            categories = categoryRepository.saveAllAndFlush(categories);
            log.info("Categories has been created: " +  categories);


            List<Subcategory> subcategories = Arrays.asList(
                    new Subcategory(
                            0,
                            "rpg",
                            categories.get(0)
                    ),
                    new Subcategory(
                            0,
                            "shooter",
                            categories.get(0)
                    )
            );
            List<Subcategory> createdSubcategories = subcategoryRepository.saveAll(subcategories);
            log.info("Subcategories has been created: " + createdSubcategories);
        };
    }
}
