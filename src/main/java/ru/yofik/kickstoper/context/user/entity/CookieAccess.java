package ru.yofik.kickstoper.context.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CookieAccess {
    private final String sessionId;


    public static CookieAccess generateFor(User user) {
        UUID uuid = UUID.randomUUID();
        String encodedUserEmail = Base64.getEncoder().encodeToString(user.getEmail().getBytes(StandardCharsets.UTF_8));
        String encodedUuid = Base64.getEncoder().encodeToString(uuid.toString().getBytes(StandardCharsets.UTF_8));
        return new CookieAccess(
                encodedUserEmail + "." + encodedUuid
        );
    }
}
