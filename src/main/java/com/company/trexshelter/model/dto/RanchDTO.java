package com.company.trexshelter.model.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RanchDTO {
    private Long id;


    @NotBlank(message = "The name field can not be empty!")
    private String name;

    @Column(unique = true, nullable = false, length = 100)
    @NotBlank(message = "The address field can not be empty!")
    private String address;


    @Override
    public String toString() {
        return
                "id=" + id + " address=" + address;
    }
}
