package ru.yofik.bank.context.transaction.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import ru.yofik.bank.context.transaction.view.TransactionView;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * Expiration time can be set via @RedisHash annotation
 * Now expiration time is set for 6 minutes
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "transaction", timeToLive = 300L)
public class Transaction implements Serializable {
    private static final long serialVersionUID = 7870953607416094663L;

    @Id
    private String id;

    @NotBlank
    @Column(nullable = false)
    private String fromAccount;

    @NotBlank
    @Column(nullable = false)
    private String toAccount;

    @Positive
    @Column(nullable = false)
    private int amount;

    public TransactionView toTransactionView() {
        return new TransactionView(
                fromAccount,
                toAccount,
                amount
        );
    }
}



