package ru.yofik.kickstoper.context.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CookieAccess {
    private final String sessionId;
}
