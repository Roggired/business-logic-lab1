package ru.yofik.kickstoper.context.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yofik.kickstoper.context.user.service.PasswordEncryption;

import javax.persistence.*;
import java.util.Base64;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "user_seq", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Role role;


    public void securePassword() {
        PasswordEncryption passwordEncryption = new PasswordEncryption();
        byte[] passwordBytes = Base64.getDecoder().decode(password);
        password = passwordEncryption.encrypt(passwordBytes);
    }
}
