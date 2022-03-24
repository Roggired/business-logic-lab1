package ru.yofik.bank.context.account.api.requests;

import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
public class CreateAccountRequest {
    @NotBlank
    public final String name;

    @NotBlank
    public final String surname;
}
