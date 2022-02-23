package ru.yofik.kickstoper.domain.entity.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "finance_data")
public class FinanceData {
    @Id
    @GeneratedValue(generator = "finance_data_seq", strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private int id;

    @Column
    @NotNull
    private String bankAccount;

    @Column
    @NotNull
    private String bankName;

    @Column
    @NotNull
    private String bankCountry;
}
