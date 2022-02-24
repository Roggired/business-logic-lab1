package ru.yofik.kickstoper.domain.entity.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(generator = "comments_seq", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false)
    private String text;

    @OneToOne
    @JoinColumn(name = "application_id", nullable = false)
    @JsonIgnore
    private Application application;
}
