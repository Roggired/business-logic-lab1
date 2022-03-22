package ru.yofik.kickstoper.context.user.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yofik.kickstoper.context.user.api.requests.UserLoginRequest;
import ru.yofik.kickstoper.context.user.api.requests.UserRegisterRequest;
import ru.yofik.kickstoper.context.user.entity.CookieAccess;
import ru.yofik.kickstoper.context.user.entity.User;
import ru.yofik.kickstoper.context.user.exception.UserAlreadyExistsException;
import ru.yofik.kickstoper.context.user.exception.UserNotExistsException;
import ru.yofik.kickstoper.context.user.exception.WrongPasswordException;
import ru.yofik.kickstoper.context.user.factory.UserFactory;
import ru.yofik.kickstoper.context.user.repository.UserRepository;

import java.util.Optional;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFactory userFactory;


    @Override
    public CookieAccess register(UserRegisterRequest request) {
        User newUser = userFactory.from(request);
        Optional<User> existedUser = userRepository.findUserByEmail(newUser.getEmail());

        if (existedUser.isPresent()) {
            log.warn("User with email: " + newUser.getEmail() + " already exists");
            throw new UserAlreadyExistsException();
        }

        // TODO Spring JTA
        User user = userRepository.save(newUser);
        log.info(() -> "User with email: " + user.getEmail() + " has been registered");

        return CookieAccess.generateFor(user);
    }

    @Override
    public CookieAccess login(UserLoginRequest request) {
        Optional<User> existedUser = userRepository.findUserByEmail(request.email);
        if (!existedUser.isPresent()) {
            log.warn("No user with email: " + request.email);
            throw new UserNotExistsException();
        }

        User possibleUser = userFactory.from(request);
        User targetUser = existedUser.get();

        if (!possibleUser.getPassword().equals(targetUser.getPassword())) {
            log.warn("Wrong password for user with email: " + request.email);
            throw new WrongPasswordException();
        }

        log.info(() -> "Use with email: " + request.email + " successfully logged in");
        return CookieAccess.generateFor(targetUser);
    }
}
