package ru.yofik.kickstoper.context.project.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SupportProjectRequest {
    public final long amount;
    public final String fromAccount;
    public final String toAccount;
}
