package com.company.trexshelter.model.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RanchDTO {
    private Long id;


    @NotBlank(message = "{name.notblank}")
    private String name;

    @Column(unique = true, nullable = false, length = 100)
    @NotBlank(message = "{address.notblank}")
    private String address;

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + ", address=" + address;
    }
}
