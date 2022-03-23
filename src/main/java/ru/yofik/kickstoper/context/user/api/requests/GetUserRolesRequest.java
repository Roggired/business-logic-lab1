package ru.yofik.kickstoper.context.user.api.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUserRolesRequest {
    public final String email;
}
