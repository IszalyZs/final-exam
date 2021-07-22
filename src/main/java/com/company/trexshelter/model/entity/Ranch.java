package com.company.trexshelter.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Ranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false,length = 100)
    @NotBlank(message = "The field must not be empty.")
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "ranch", cascade = CascadeType.ALL)
    private List<Dog> dogs = new ArrayList<>();
}
