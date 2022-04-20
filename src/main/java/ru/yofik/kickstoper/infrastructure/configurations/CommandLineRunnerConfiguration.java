package ru.yofik.kickstoper.infrastructure.configurations;

import lombok.extern.java.Log;
import org.quartz.*;
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
import java.util.UUID;

@Configuration
@Log
public class CommandLineRunnerConfiguration {
    @Bean
    public CommandLineRunner produceCommandLineRunner(
            @Autowired CategoryRepository categoryRepository,
            @Autowired SubcategoryRepository subcategoryRepository,
            @Autowired Scheduler quartzScheduler,
            @Autowired JobDetail projectDiscoverJobDetails,
            @Autowired JobDetail projectDeleteFinishedJobDetails
            ) {
        return args -> {
            List<Category> categories = List.of(
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

            scheduleJob(
                    quartzScheduler,
                    projectDiscoverJobDetails,
                    30
            );

            scheduleJob(
                    quartzScheduler,
                    projectDeleteFinishedJobDetails,
                    60
            );
        };
    }

    private static void scheduleJob(
            Scheduler scheduler,
            JobDetail jobDetail,
            int everySeconds
    ) throws SchedulerException {
        final String jobName = UUID.randomUUID().toString();
        final Trigger jobTrigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobName, "discoverers")
                .withSchedule(
                        SimpleScheduleBuilder.repeatSecondlyForever(everySeconds)
                )
                .build();

        scheduler.scheduleJob(jobTrigger);
    }
}
