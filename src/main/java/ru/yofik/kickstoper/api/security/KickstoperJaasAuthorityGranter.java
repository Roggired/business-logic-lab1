package ru.yofik.kickstoper.api.security;

import org.springframework.security.authentication.jaas.AuthorityGranter;
import ru.yofik.kickstoper.context.user.api.requests.GetUserRolesRequest;
import ru.yofik.kickstoper.context.user.entity.Role;
import ru.yofik.kickstoper.context.user.service.UserService;
import ru.yofik.kickstoper.infrastructure.injection.SpringContext;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class KickstoperJaasAuthorityGranter implements AuthorityGranter {
    @Override
    public Set<String> grant(Principal principal) {
        UserService userService = SpringContext.getBean(UserService.class);
        GetUserRolesRequest request = new GetUserRolesRequest(principal.getName());

        return userService.getRolesOf(request)
                .stream()
                .map(Role::name)
                .collect(Collectors.toCollection(HashSet::new));
    }
}
