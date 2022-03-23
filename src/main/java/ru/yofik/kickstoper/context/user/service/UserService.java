package ru.yofik.kickstoper.context.user.service;

import org.springframework.stereotype.Service;
import ru.yofik.kickstoper.context.user.api.requests.GetUserRolesRequest;
import ru.yofik.kickstoper.context.user.api.requests.UserLoginRequest;
import ru.yofik.kickstoper.context.user.api.requests.UserRegisterRequest;
import ru.yofik.kickstoper.context.user.model.CookieAccess;
import ru.yofik.kickstoper.context.user.model.Role;

import java.util.Set;

@Service
public interface UserService {
    CookieAccess register(UserRegisterRequest request);
    CookieAccess login(UserLoginRequest request);
    Set<Role> getRolesOf(GetUserRolesRequest request);
}
