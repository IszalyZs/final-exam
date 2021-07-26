package com.company.trexshelter.mappers;

import com.company.trexshelter.model.dto.BreedDTO;
import com.company.trexshelter.model.entity.Breed;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BreedToBreedDTO {
    private final ModelMapper modelMapper;

    @Autowired
    public BreedToBreedDTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BreedDTO getBreedDTO(Breed breed) {
        return modelMapper.map(breed, BreedDTO.class);
    }
}
