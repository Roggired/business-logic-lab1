package ru.yofik.kickstoper.context.user.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yofik.kickstoper.context.user.api.requests.UserLoginRequest;
import ru.yofik.kickstoper.context.user.api.requests.UserRegisterRequest;
import ru.yofik.kickstoper.context.user.entity.CookieAccess;
import ru.yofik.kickstoper.context.user.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v2/auth")
public class AuthResource {
    @Autowired
    private UserService userService;

    @Value("${auth.session.max-age}")
    private int sessionMaxAgeInSecs;

    @Value("${auth.session.key}")
    private String cookieKey;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterRequest request, HttpServletResponse servletResponse) {
        CookieAccess access = userService.register(request);
        setCookie(access, servletResponse);
        return ResponseEntity
                .created(URI.create("/api/v2/users"))
                .build();
    }

    @PutMapping
    public void login(@RequestBody @Valid UserLoginRequest request, HttpServletResponse servletResponse) {
        CookieAccess access = userService.login(request);
        setCookie(access, servletResponse);
    }

    private void setCookie(CookieAccess access, HttpServletResponse servletResponse) {
        Cookie cookie = new Cookie(cookieKey, access.getSessionId());
        cookie.setMaxAge(sessionMaxAgeInSecs);
        servletResponse.addCookie(cookie);
    }
}
