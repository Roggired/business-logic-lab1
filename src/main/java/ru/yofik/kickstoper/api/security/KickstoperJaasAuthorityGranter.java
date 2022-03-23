package ru.yofik.kickstoper.api.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import ru.yofik.kickstoper.context.user.api.requests.GetUserRolesRequest;
import ru.yofik.kickstoper.context.user.model.Role;
import ru.yofik.kickstoper.context.user.service.UserService;
import ru.yofik.kickstoper.infrastructure.injection.SpringContext;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
public class KickstoperJaasAuthorityGranter implements AuthorityGranter {
    @Override
    public Set<String> grant(Principal principal) {
        UserService userService = SpringContext.getBean(UserService.class);
        GetUserRolesRequest request = new GetUserRolesRequest(principal.getName());


        Set<String> roles = userService.getRolesOf(request)
                .stream()
                .map(Role::name)
                .collect(Collectors.toCollection(HashSet::new));

        log.info(() -> "Roles of : " + principal.getName() + " is : " + roles);
        return roles;
    }
}
