package com.company.trexshelter.service;

import com.company.trexshelter.exception.BreedException;
import com.company.trexshelter.converter.BreedDTOToBreed;
import com.company.trexshelter.converter.BreedToBreedDTO;
import com.company.trexshelter.model.dto.BreedDTO;
import com.company.trexshelter.model.entity.Breed;
import com.company.trexshelter.repository.BreedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BreedService {
    private final BreedRepository breedRepository;
    private final BreedDTOToBreed breedDTOToBreed;
    private final BreedToBreedDTO breedToBreedDTO;

    @Autowired
    public BreedService(BreedRepository breedRepository, BreedDTOToBreed breedDTOToBreed, BreedToBreedDTO breedToBreedDTO) {
        this.breedRepository = breedRepository;
        this.breedDTOToBreed = breedDTOToBreed;
        this.breedToBreedDTO = breedToBreedDTO;
    }

    public List<BreedDTO> findAll() {
        List<Breed> breeds = breedRepository.findAll();
        if (breeds.size() == 0) {
            throw new BreedException("The breed entities do not exist!");
        }
        return breeds.stream().map(breedToBreedDTO::getBreedDTO).collect(Collectors.toList());
    }

    public BreedDTO findById(Long id) {
        Optional<Breed> optionalBreed = breedRepository.findById(id);
        if (optionalBreed.isPresent()) {
            Breed breed = optionalBreed.get();
            return breedToBreedDTO.getBreedDTO(breed);
        }
        throw new BreedException("The breed entity does not exist with id: " + id + "!");
    }

    public void deleteById(Long id) {
        try {
            breedRepository.deleteById(id);
        } catch (Exception exception) {
            throw new BreedException("No breed entity with id: " + id + "!");
        }
    }

    public BreedDTO save(BreedDTO breedDTO) {
        Breed breed = breedRepository.save(breedDTOToBreed.getBreed(breedDTO));
        return breedToBreedDTO.getBreedDTO(breed);
    }
}
