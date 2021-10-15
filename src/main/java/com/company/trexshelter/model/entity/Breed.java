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
@NoArgsConstructor
@AllArgsConstructor
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    @NotBlank(message = "{name.notblank}")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "breed", cascade = CascadeType.ALL)
    private List<Dog> dogs = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("id=%d, name=%s",this.id,this.name);
    }
}
