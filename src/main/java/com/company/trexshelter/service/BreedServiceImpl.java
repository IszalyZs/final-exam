package com.company.trexshelter.service;

import com.company.trexshelter.exception.BreedException;
import com.company.trexshelter.mappers.BreedDTOToBreed;
import com.company.trexshelter.mappers.BreedToBreedDTO;
import com.company.trexshelter.model.dto.BreedDTO;
import com.company.trexshelter.model.entity.Breed;
import com.company.trexshelter.repository.BreedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BreedServiceImpl implements BreedService {
    private final BreedRepository breedRepository;
    private final BreedDTOToBreed breedDTOToBreed;
    private final BreedToBreedDTO breedToBreedDTO;

    @Autowired
    public BreedServiceImpl(BreedRepository breedRepository, BreedDTOToBreed breedDTOToBreed, BreedToBreedDTO breedToBreedDTO) {
        this.breedRepository = breedRepository;
        this.breedDTOToBreed = breedDTOToBreed;
        this.breedToBreedDTO = breedToBreedDTO;
    }

    @Override
    public List<BreedDTO> findAll() {
        List<Breed> breeds = breedRepository.findAll();
        if (breeds.size() == 0) {
            throw new BreedException("The Breed entities are not exists!");
        }
        return breeds.stream().map(breedToBreedDTO::getBreedDTO).collect(Collectors.toList());
    }

    @Override
    public BreedDTO findById(Long id) {
        Optional<Breed> optionalBreed = breedRepository.findById(id);
        if (optionalBreed.isPresent()) {
            Breed breed = optionalBreed.get();
            return breedToBreedDTO.getBreedDTO(breed);
        }
        throw new BreedException("The Breed entity is not exists with id: " + id + "!");
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void save(BreedDTO breedDTO) {

    }
}
