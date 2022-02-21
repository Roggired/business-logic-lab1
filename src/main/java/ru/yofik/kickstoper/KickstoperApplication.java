package ru.yofik.kickstoper;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.yofik.kickstoper.domain.entity.category.Category;
import ru.yofik.kickstoper.domain.entity.subcategory.Subcategory;
import ru.yofik.kickstoper.domain.service.subcategory.SubcategoryService;
import ru.yofik.kickstoper.storage.sql.category.CategoryRepository;
import ru.yofik.kickstoper.storage.sql.subcategory.SubcategoryRepository;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@Log4j2
public class KickstoperApplication {

	public static void main(String[] args) {
		SpringApplication.run(KickstoperApplication.class, args);
	}

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
