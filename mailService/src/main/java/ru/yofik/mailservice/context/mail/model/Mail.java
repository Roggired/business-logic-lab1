package ru.yofik.mailservice.context.mail.model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Mail {
    private String recipient;
    private String title;
    private String body;
}
