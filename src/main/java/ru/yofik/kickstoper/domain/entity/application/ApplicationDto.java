package ru.yofik.kickstoper.domain.entity.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDto {
    private int id;
    private String applicationStatus;
    @NotBlank
    @Length(min = 3, max = 128)
    private String projectName;
    @Positive
    private int categoryId;
    @PositiveOrZero
    private int subcategoryId;
    @NotBlank
    private String shortDescription;
    @Positive
    private long targetBudget;
    @NotBlank
    private String projectEndDate;
}
