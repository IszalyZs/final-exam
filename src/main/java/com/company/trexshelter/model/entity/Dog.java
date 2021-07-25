package com.company.trexshelter.model.entity;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@Entity
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "[0-9]{15}", message = "The chip code length should be exactly 15 numbers!")
    @Column(unique = true, nullable = false, length = 15)
    private String chipCode;


    @Range(min = 1, max = 240, message = "Age is calculated in months between 1 and 240!")
    @NotNull(message = "The ageInMonth field can not be empty!")
    private Integer ageInMonth;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The gender field can not be empty!")
    private Gender gender;

    @ManyToOne
    @NotNull(message = "The ranch field can not be empty!")
    private Ranch ranch;

    @ManyToOne
    @NotNull(message = "The breed field can not be empty!")
    private Breed breed;

}
