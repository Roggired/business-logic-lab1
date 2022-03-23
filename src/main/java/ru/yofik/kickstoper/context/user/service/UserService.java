package ru.yofik.kickstoper.context.user.service;

import org.springframework.stereotype.Service;
import ru.yofik.kickstoper.context.user.api.requests.GetUserRolesRequest;
import ru.yofik.kickstoper.context.user.api.requests.UserLoginRequest;
import ru.yofik.kickstoper.context.user.api.requests.UserRegisterRequest;
import ru.yofik.kickstoper.context.user.entity.CookieAccess;
import ru.yofik.kickstoper.context.user.entity.Role;

import java.util.Set;

@Service
public interface UserService {
    CookieAccess register(UserRegisterRequest request);
    CookieAccess login(UserLoginRequest request);
    Set<Role> getRolesOf(GetUserRolesRequest request);
}
