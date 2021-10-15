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
        return String.format("id=%d, name=%s, address=%s",this.id,this.name,this.address);
    }
}
