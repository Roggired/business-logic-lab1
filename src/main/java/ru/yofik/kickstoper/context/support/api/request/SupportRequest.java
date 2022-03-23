package ru.yofik.kickstoper.context.support.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
public class SupportRequest {
    @Positive
    public final int projectId;
    @NotBlank
    public final String accountId;
    @Positive
    public final int amount;
}
