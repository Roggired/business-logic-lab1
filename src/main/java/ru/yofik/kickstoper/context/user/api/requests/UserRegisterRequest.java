package ru.yofik.kickstoper.context.user.api.requests;

import lombok.RequiredArgsConstructor;
import ru.yofik.kickstoper.context.user.entity.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
public class UserRegisterRequest {
    @NotBlank
    public final String name;
    @NotBlank
    public final String surname;
    @NotBlank
    public final String email;
    @NotBlank
    public final String passwordBase64;
    @NotNull
    public final Role role;
}
