package ru.yofik.common;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "mail_tasks")

//TODO нельзя использовать напрямую! Из-за конфигураций Spring Data нужно взять копию этого класса к себе в модуль!!!

public class MailTask {
    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String body;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> recipients;
}
