package com.company.trexshelter.mappers;

import com.company.trexshelter.model.dto.BreedDTO;
import com.company.trexshelter.model.entity.Breed;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BreedDTOToBreed {
    private final ModelMapper modelMapper;

    @Autowired
    public BreedDTOToBreed(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Breed getBreed(BreedDTO breedDTO) {
        return modelMapper.map(breedDTO, Breed.class);
    }
}
