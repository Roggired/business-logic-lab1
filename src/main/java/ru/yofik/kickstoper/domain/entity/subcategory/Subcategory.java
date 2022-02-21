package ru.yofik.kickstoper.domain.entity.subcategory;

import lombok.*;
import ru.yofik.kickstoper.domain.entity.category.Category;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subcategory")
@ToString
public class Subcategory {
    @Id
    @GeneratedValue(generator = "subcategory_seq", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
