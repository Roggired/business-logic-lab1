package ru.yofik.kickstoper.context.support.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Transaction {
    private String fromAccountId;
    private int amount;
}
