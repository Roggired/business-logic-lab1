package ru.yofik.kickstoper.context.user.api.requests;

import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
public class UserLoginRequest {
    @NotBlank
    public final String login;
    @NotBlank
    public final String passwordBase64;
}
