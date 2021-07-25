package com.company.trexshelter.model.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BreedDTO {
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    @NotBlank(message = "The name field can not be empty!")
    private String name;


    @Override
    public String toString() {
        return "id=" + id + " name=" + name;
    }
}
