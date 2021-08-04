package com.company.trexshelter.service;

import com.company.trexshelter.model.dto.BreedDTO;

import java.util.List;

public interface BreedService {
    List<BreedDTO> findAll();

    BreedDTO findById(Long id);

    void deleteById(Long id);

    BreedDTO save(BreedDTO breedDTO);
}
