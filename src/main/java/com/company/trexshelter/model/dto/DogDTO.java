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
@NoArgsConstructor
@AllArgsConstructor
public class DogDTO {
    private Long id;
    @Pattern(regexp = "[0-9]{15}", message = "{dog.pattern}")
    @Column(unique = true, length = 15)
    private String chipCode;

    @Range(min = 1, max = 240, message = "{dog.range}")
    @NotNull(message = "{dog.agenotnull}")
    private Integer ageInMonth;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{dog.gendernotnull}")
    private Gender gender;

    @NotNull(message = "{dogdto.ranchidnotnull}")
    private Long ranchDTOId;

    @NotNull(message = "{dogdto.breedidnotnull}")
    private Long breedDTOId;

    @JsonIgnore
    private List<BreedDTO> breedDTOs;
    @JsonIgnore
    private List<RanchDTO> ranchDTOS;

    @Override
    public String toString() {
        return "DogDTO{" +
                "id=" + id +
                ", chipCode='" + chipCode + '\'' +
                ", ageInMonth=" + ageInMonth +
                ", gender=" + gender +
                ", ranchDTOId=" + ranchDTOId +
                ", breedDTOId=" + breedDTOId +
                '}';
    }
}
