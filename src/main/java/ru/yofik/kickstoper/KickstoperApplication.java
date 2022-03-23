package ru.yofik.kickstoper;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.util.unit.DataSize;
import ru.yofik.kickstoper.domain.entity.category.Category;
import ru.yofik.kickstoper.domain.entity.subcategory.Subcategory;
import ru.yofik.kickstoper.storage.sql.category.CategoryRepository;
import ru.yofik.kickstoper.storage.sql.subcategory.SubcategoryRepository;

import javax.servlet.MultipartConfigElement;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Log4j2
public class KickstoperApplication extends SpringBootServletInitializer {
	private static Class<KickstoperApplication> applicationClass = KickstoperApplication.class;

	public static void main(String[] args) {
		SpringApplication.run(KickstoperApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(applicationClass);
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

	@Value("${local.files.maxFileSize}")
	private int maxFileSize;

	@Bean
	public MultipartConfigElement provideMultipartConfig() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofMegabytes(maxFileSize));
		factory.setMaxRequestSize(DataSize.ofMegabytes(maxFileSize));
		return factory.createMultipartConfig();
	}

	@Bean
	public PlatformTransactionManager platformTransactionManager(){
		return new JtaTransactionManager();
	}
}
