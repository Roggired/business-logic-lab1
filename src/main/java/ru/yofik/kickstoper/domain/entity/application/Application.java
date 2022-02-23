package ru.yofik.kickstoper.domain.entity.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yofik.kickstoper.domain.entity.category.Category;
import ru.yofik.kickstoper.domain.entity.subcategory.Subcategory;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(generator = "application_seq", strategy = GenerationType.SEQUENCE)
    private int id;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
    //TODO не забыть привязку к Application к User, когда будет добавлена аутентификация

    @Column(nullable = false)
    private ApplicationStatus applicationStatus;

    @Column(nullable = false)
    @NotBlank
    private String projectName;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    @Column(nullable = false)
    @NotBlank
    private String shortDescription;

    @Column(nullable = false)
    @Positive
    private long targetBudget;

    @Column(nullable = false)
    @Future
    private ZonedDateTime projectEndDate;

    @OneToOne
    @JoinColumn(name = "finance_data_id")
    private FinanceData financeData;

    @Column
    private String videoFilename;

    @Column
    private String descriptionFilename;


    public ApplicationDto toDto() {
        return new ApplicationDto(
                id,
                applicationStatus.toString(),
                projectName,
                category.getId(),
                subcategory.getId(),
                shortDescription,
                targetBudget,
                projectEndDate.toInstant().toEpochMilli(),
                videoFilename,
                descriptionFilename
        );
    }
}
