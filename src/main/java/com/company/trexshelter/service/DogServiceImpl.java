package com.company.trexshelter.service;

import com.company.trexshelter.exception.DogException;
import com.company.trexshelter.mappers.DogDTOToDog;
import com.company.trexshelter.mappers.DogToDogDTO;
import com.company.trexshelter.model.dto.DogDTO;
import com.company.trexshelter.model.entity.Dog;
import com.company.trexshelter.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DogServiceImpl implements DogService {

    private final DogRepository dogRepository;
    private final DogToDogDTO dogToDogDTO;
    private final DogDTOToDog dogDTOToDog;

    @Autowired
    public DogServiceImpl(DogRepository dogRepository, DogToDogDTO dogToDogDTO, DogDTOToDog dogDTOToDog) {
        this.dogRepository = dogRepository;
        this.dogToDogDTO = dogToDogDTO;
        this.dogDTOToDog = dogDTOToDog;
    }

    @Override
    public List<Dog> findAll() {
        List<Dog> dogs = dogRepository.findAll();
        if (dogs.size() == 0) {
            throw new DogException("The dog entities are not exists!");
        }
        return dogs;
    }

    @Override
    public Dog findById(Long id) {
        Optional<Dog> optionalDog = dogRepository.findById(id);
        if (optionalDog.isPresent()) {
            Dog dog = optionalDog.get();
            return dog;
        }
        throw new DogException("The dog entity is not exists with id: " + id + "!");
    }

    @Override
    public void deleteById(Long id) {
        try {
            dogRepository.deleteById(id);
        } catch (Exception exception) {
            throw new DogException("No dog entity with id: " + id + "!");
        }
    }

    @Override
    public Dog save(DogDTO dogDTO) {
        return dogRepository.save(dogDTOToDog.getDog(dogDTO));
    }

    @Override
    public List<Dog> findAllByChipCode(String chipCode) {
        List<Dog> dogs = dogRepository.findDogsByChipCode(chipCode);
        if (dogs.size() > 0) return dogs;
        else throw new DogException("The dog entity is not exists with chip code: " + chipCode + "!");
    }

    @Override
    public List<Dog> findAllByBreedsName(String name) {
        List<Dog> dogs = dogRepository.findDogsByBreed_Name(name);
        if (dogs.size() == 0)
            throw new DogException("The dog entities are not exists with breed's name: " + name + "!");
        else return dogs;
    }

    @Override
    public List<Dog> findAllByRanchId(Long id) {
        List<Dog> dogs = dogRepository.findDogsByRanchId(id);
        if (dogs.size() == 0)
            throw new DogException("The dog entities are not exists with ranch's id: " + id + "!");
        else return dogs;
    }

    @Override
    public List<Dog> findDogsByBreed_NameAndRanch_Id(String breedsName, Long id) {
        List<Dog> dogs = dogRepository.findDogsByBreed_NameAndRanch_Id(breedsName, id);
        if (dogs.size() == 0)
            throw new DogException("The dog entities are not exists with breed's name: " + breedsName + " and ranch's id: " + id + "!");
        else return dogs;
    }




}
