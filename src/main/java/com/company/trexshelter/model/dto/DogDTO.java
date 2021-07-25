package com.company.trexshelter.model.dto;

import com.company.trexshelter.model.entity.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;


@Getter
@Setter
public class DogDTO {
    private Long id;
    @Pattern(regexp = "[0-9]{15}", message = "The chip code length should be exactly 15 numbers!")
    @Column(unique = true, length = 15)
    private String chipCode;

    @Range(min = 1, max = 240, message = "Age is calculated in months between 1 and 240!")
    @NotNull(message = "The ageInMonth field can not be empty!")
    private Integer ageInMonth;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The gender field can not be empty!")
    private Gender gender;

    @NotNull(message = "The ranchDTOId field can not be empty!")
    private Long ranchDTOId;

    @NotNull(message = "The breedDTOId field can not be empty!")
    private Long breedDTOId;

    @JsonIgnore
    private List<BreedDTO> breedDTOs;
    @JsonIgnore
    private List<RanchDTO> ranchDTOS;

}
