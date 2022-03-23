package ru.yofik.kickstoper.context.support.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Account {
    private String id;
    private int balance;
    private String name;
    private String surname;
}
