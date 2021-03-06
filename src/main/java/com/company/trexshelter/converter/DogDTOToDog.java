package com.company.trexshelter.converter;

import com.company.trexshelter.model.dto.BreedDTO;
import com.company.trexshelter.model.dto.DogDTO;
import com.company.trexshelter.model.dto.RanchDTO;
import com.company.trexshelter.model.entity.Breed;
import com.company.trexshelter.model.entity.Dog;
import com.company.trexshelter.model.entity.Ranch;
import com.company.trexshelter.service.BreedService;
import com.company.trexshelter.service.RanchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DogDTOToDog {
    private final ModelMapper modelMapper;
    private final RanchService ranchService;
    private final BreedService breedService;

    @Autowired
    public DogDTOToDog(ModelMapper modelMapper, RanchService ranchService, BreedService breedService) {
        this.modelMapper = modelMapper;
        this.ranchService = ranchService;
        this.breedService = breedService;
    }

    public Dog getDog(DogDTO dogDTO) {
        Dog dog = modelMapper.map(dogDTO, Dog.class);

        Long ranchDTOId = dogDTO.getRanchDTOId();
        RanchDTO ranchDTO = ranchService.findById(ranchDTOId);
        dog.setRanch(modelMapper.map(ranchDTO, Ranch.class));

        Long breedDTOId = dogDTO.getBreedDTOId();
        BreedDTO breedDTO = breedService.findById(breedDTOId);
        dog.setBreed(modelMapper.map(breedDTO, Breed.class));

        return dog;
    }
}
