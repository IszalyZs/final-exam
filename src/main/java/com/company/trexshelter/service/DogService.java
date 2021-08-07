package com.company.trexshelter.service;

import com.company.trexshelter.model.dto.DogDTO;
import com.company.trexshelter.model.entity.Dog;

import java.util.List;

public interface DogService {
    List<Dog> findAll();

    Dog findById(Long id);

    void deleteById(Long id);

    Dog save(DogDTO dogDTO);

    List<Dog> findAllByChipCode(String chipCode);

    List<Dog> findAllByBreedsName(String name);

    List<Dog> findAllByRanchId(Long id);

    List<Dog> findDogsByBreed_NameAndRanch_Id(String breedsName, Long id);
}
