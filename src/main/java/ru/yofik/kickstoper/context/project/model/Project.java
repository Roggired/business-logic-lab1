package ru.yofik.kickstoper.context.project.model;

import lombok.*;
import ru.yofik.kickstoper.context.application.model.Category;
import ru.yofik.kickstoper.context.application.model.FinanceData;
import ru.yofik.kickstoper.context.application.model.Subcategory;
import ru.yofik.kickstoper.context.project.view.ProjectPreview;
import ru.yofik.kickstoper.context.project.view.ProjectView;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(generator = "project_seq", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false)
    private String projectName;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    @Column(nullable = false)
    private String shortDescription;

    @Column(nullable = false)
    private long targetBudget;

    @Column(nullable = false)
    private long balance;

    @Column(nullable = false)
    private ZonedDateTime projectEndDate;

    @OneToOne
    @JoinColumn(name = "finance_data_id")
    private FinanceData financeData;

    @Column
    private String videoFilename;

    @Column
    private String descriptionFilename;


    public ProjectPreview getPreview() {
        return new ProjectPreview(
                id,
                projectName,
                category,
                subcategory,
                shortDescription,
                targetBudget,
                projectEndDate
        );
    }

    public ProjectView getView() {
        return new ProjectView(
                id,
                projectName,
                category,
                subcategory,
                shortDescription,
                targetBudget,
                balance,
                projectEndDate,
                videoFilename,
                descriptionFilename
        );
    }
}
