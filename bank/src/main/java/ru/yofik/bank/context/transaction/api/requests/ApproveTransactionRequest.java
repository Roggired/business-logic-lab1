package ru.yofik.bank.context.transaction.api.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApproveTransactionRequest {
    @Positive
    public int code;
}
