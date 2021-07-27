package com.company.trexshelter.model.entity;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "[0-9]{15}", message = "{dog.pattern}")
    @Column(unique = true, nullable = false, length = 15)
    private String chipCode;


    @Range(min = 1, max = 240, message = "{dog.range}")
    @NotNull(message = "{dog.agenotnull}")
    private Integer ageInMonth;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{dog.gendernotnull}")
    private Gender gender;

    @ManyToOne
    @NotNull(message = "{dog.ranchnotnull}")
    private Ranch ranch;

    @ManyToOne
    @NotNull(message = "{dog.breednotnull}")
    private Breed breed;

}
