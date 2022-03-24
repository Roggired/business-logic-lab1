package ru.yofik.bank.context.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yofik.bank.context.account.model.Account;

import javax.validation.constraints.NotBlank;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByNameAndSurname(@NotBlank String name, @NotBlank String surname);

    Account findByAccountId(@NotBlank String accountId);
}
