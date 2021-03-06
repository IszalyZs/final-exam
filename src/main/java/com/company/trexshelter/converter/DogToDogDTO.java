package com.company.trexshelter.converter;

import com.company.trexshelter.model.dto.DogDTO;
import com.company.trexshelter.model.entity.Dog;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DogToDogDTO {
    private final ModelMapper modelMapper;

    @Autowired
    public DogToDogDTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DogDTO getDogDTO(Dog dog) {
        DogDTO dogDTO = modelMapper.map(dog, DogDTO.class);
        if (dog.getRanch() != null)
            dogDTO.setRanchDTOId(dog.getRanch().getId());
        if (dog.getBreed() != null)
            dogDTO.setBreedDTOId(dog.getBreed().getId());
        return dogDTO;
    }
}
