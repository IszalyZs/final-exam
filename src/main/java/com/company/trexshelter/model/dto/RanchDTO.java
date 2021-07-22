package com.company.trexshelter.model.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RanchDTO {
    private Long id;

    @Column(unique = true,nullable = false,length = 100)
    @NotBlank(message = "The field must not be empty.")
    private String address;
}
