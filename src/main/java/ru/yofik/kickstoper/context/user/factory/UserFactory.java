package ru.yofik.kickstoper.context.user.factory;

import org.springframework.stereotype.Component;
import ru.yofik.kickstoper.context.user.api.requests.UserLoginRequest;
import ru.yofik.kickstoper.context.user.api.requests.UserRegisterRequest;
import ru.yofik.kickstoper.context.user.model.User;

@Component
public class UserFactory {
    public User from(UserRegisterRequest request) {
        User user = new User();
        user.setName(request.name);
        user.setEmail(request.email);
        user.setPassword(request.passwordBase64);
        user.setSurname(request.surname);
        user.setRole(request.role);
        user.securePassword();
        return user;
    }

    public User from(UserLoginRequest request) {
        User user = new User();
        user.setEmail(request.email);
        user.setPassword(request.passwordBase64);
        user.securePassword();
        return user;
    }
}
