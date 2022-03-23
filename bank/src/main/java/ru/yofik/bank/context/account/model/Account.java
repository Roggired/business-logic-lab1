package ru.yofik.bank.context.account.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yofik.bank.context.account.view.AccountView;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(generator = "account_seq", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private int pinCode;

    private int balance;

    public AccountView toAccountView() {
        return new AccountView(
                name,
                surname,
                balance
        );
    }
}
