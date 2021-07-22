package com.company.trexshelter.service;

import com.company.trexshelter.model.dto.DogDTO;
import com.company.trexshelter.model.entity.Dog;

import java.util.List;

public interface DogService {
    List<Dog> findAll();
    DogDTO findById(Long id);
    void deleteById(Long id);
    DogDTO save(DogDTO dogDTO);
    //List<Dog> findByBreedsId(Long breedId);
}
