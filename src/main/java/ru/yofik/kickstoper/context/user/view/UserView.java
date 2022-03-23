package ru.yofik.kickstoper.context.user.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yofik.kickstoper.context.user.model.Role;

@Data
@AllArgsConstructor
public class UserView {
    private final String name;
    private final String surname;
    private final String email;
    private final Role role;
}
