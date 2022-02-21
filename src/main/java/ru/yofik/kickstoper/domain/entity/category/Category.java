package ru.yofik.kickstoper.domain.entity.category;

import lombok.*;
import ru.yofik.kickstoper.domain.entity.subcategory.Subcategory;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
@ToString
public class Category {
    @Id
    @GeneratedValue(generator = "category_seq", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<Subcategory> subcategories;


    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
