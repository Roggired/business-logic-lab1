package ru.yofik.kickstoper.context.user.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class PasswordEncryption {
    public String encrypt(byte[] passwordBytes) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] bytes = messageDigest.digest(passwordBytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
}
