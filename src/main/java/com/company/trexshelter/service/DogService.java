package com.company.trexshelter.service;

import com.company.trexshelter.exception.DogException;
import com.company.trexshelter.converter.DogDTOToDog;
import com.company.trexshelter.model.dto.DogDTO;
import com.company.trexshelter.model.entity.Dog;
import com.company.trexshelter.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DogService {

    private final DogRepository dogRepository;
    private final DogDTOToDog dogDTOToDog;

    @Autowired
    public DogService(DogRepository dogRepository, DogDTOToDog dogDTOToDog) {
        this.dogRepository = dogRepository;
        this.dogDTOToDog = dogDTOToDog;
    }

    public List<Dog> findAll() {
        List<Dog> dogs = dogRepository.findAll();
        if (dogs.size() == 0) {
            throw new DogException("The dog entities do not exist!");
        }
        return dogs;
    }

    public Dog findById(Long id) {
        Optional<Dog> optionalDog = dogRepository.findById(id);
        if (optionalDog.isPresent()) {
            return optionalDog.get();
        }
        throw new DogException("The dog entity does not exist with id: " + id + "!");
    }

    public void deleteById(Long id) {
        try {
            dogRepository.deleteById(id);
        } catch (Exception exception) {
            throw new DogException("No dog entity with id: " + id + "!");
        }
    }

    public Dog save(DogDTO dogDTO) {
        return dogRepository.save(dogDTOToDog.getDog(dogDTO));
    }

    public List<Dog> findAllByChipCode(String chipCode) {
        List<Dog> dogs = dogRepository.findDogsByChipCode(chipCode);
        if (dogs.size() > 0) return dogs;
        else throw new DogException("The dog entity does not exist with chip code: " + chipCode + "!");
    }

    public List<Dog> findAllByBreedsName(String name) {
        List<Dog> dogs = dogRepository.findDogsByBreed_Name(name);
        if (dogs.size() == 0)
            throw new DogException("The dog entities do not exist with breed's name: " + name + "!");
        else return dogs;
    }

    public List<Dog> findAllByRanchId(Long id) {
        List<Dog> dogs = dogRepository.findDogsByRanchId(id);
        if (dogs.size() == 0)
            throw new DogException("The dog entities do not exist with ranch's id: " + id + "!");
        else return dogs;
    }

    public List<Dog> findDogsByBreed_NameAndRanch_Id(String breedsName, Long id) {
        List<Dog> dogs = dogRepository.findDogsByBreed_NameAndRanch_Id(breedsName, id);
        if (dogs.size() == 0)
            throw new DogException("The dog entities do not exist with breed's name: " + breedsName + " and ranch's id: " + id + "!");
        else return dogs;
    }
}
