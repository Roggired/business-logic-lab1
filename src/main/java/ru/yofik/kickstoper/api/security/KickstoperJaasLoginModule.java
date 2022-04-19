package ru.yofik.kickstoper.api.security;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import ru.yofik.kickstoper.context.user.api.requests.UserLoginRequest;
import ru.yofik.kickstoper.context.user.service.UserService;
import ru.yofik.kickstoper.infrastructure.injection.SpringContext;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.spi.LoginModule;
import java.security.Principal;
import java.util.Map;

@Log4j2
public class KickstoperJaasLoginModule implements LoginModule {
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Principal loggedInPrincipal;


    @SneakyThrows
    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        log.info(() -> "Login module has been initialized");
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    @SneakyThrows
    @Override
    public boolean login() {
        log.info(() -> "Login module LOGIN");

        NameCallback nameCallback = new NameCallback("u");
        PasswordCallback passwordCallback = new PasswordCallback("p", false);
        callbackHandler.handle(new Callback[] { nameCallback, passwordCallback });

        String userEmail = nameCallback.getName();
        String userPasswordBase64 = new String(passwordCallback.getPassword());

        UserLoginRequest userLoginRequest = new UserLoginRequest(userEmail, userPasswordBase64);
        UserService userService = SpringContext.getBean(UserService.class);

        try {
            userService.login(userLoginRequest);
            loggedInPrincipal = () -> userEmail;
        } catch (Throwable t) {
            log.fatal(t);
            return false;
        }

        return true;
    }

    @Override
    public boolean commit() {
        log.info(() -> "Login module COMMIT");

        if (loggedInPrincipal == null) {
            log.fatal("No logged in principal");
            return false;
        }

        subject.getPrincipals().add(loggedInPrincipal);
        return true;
    }

    @Override
    public boolean abort() {
        log.info(() -> "Login module ABORT");

        if (loggedInPrincipal != null) {
            subject.getPrincipals().remove(loggedInPrincipal);
            loggedInPrincipal = null;
        }

        subject = null;
        return true;
    }

    @Override
    public boolean logout() {
        log.info(() -> "Login module LOGOUT");
        if (loggedInPrincipal != null) {
            subject.getPrincipals().remove(loggedInPrincipal);
            loggedInPrincipal = null;
        }

        subject = null;

        return true;
    }
}
