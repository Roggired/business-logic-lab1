package ru.yofik.kickstoper.context.user.api.requests;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

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
}
