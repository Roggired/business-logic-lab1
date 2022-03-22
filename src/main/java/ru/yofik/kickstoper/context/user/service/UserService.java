package ru.yofik.kickstoper.context.user.service;

import org.springframework.stereotype.Service;
import ru.yofik.kickstoper.context.user.api.requests.UserLoginRequest;
import ru.yofik.kickstoper.context.user.api.requests.UserRegisterRequest;
import ru.yofik.kickstoper.context.user.entity.CookieAccess;

@Service
public interface UserService {
    CookieAccess register(UserRegisterRequest request);
    CookieAccess login(UserLoginRequest request);
}
