package com.company.trexshelter.model.dto;

import com.company.trexshelter.model.entity.Gender;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Getter
@Setter
public class DogDTO {
    private Long id;
    @Pattern(regexp = "[0-9]{15}", message = "The chip code length must be exactly 15 characters.")
    @Column(unique = true, nullable = false,length = 15)
    private String chipCode;

    @Range(min = 1, max = 240, message = "Age is calculated in months between 1 and 240.")
    @NotNull(message = "The field must not be empty.")
    private Integer ageInMonth;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The field must not be empty.")
    private Gender gender;

    @ManyToOne
    private RanchDTO ranchDTO;

    @ManyToOne
    private BreedDTO breedDTO;
}
