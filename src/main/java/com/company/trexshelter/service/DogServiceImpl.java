package com.company.trexshelter.service;

import com.company.trexshelter.exception.DogException;
import com.company.trexshelter.model.dto.BreedDTO;
import com.company.trexshelter.model.dto.DogDTO;
import com.company.trexshelter.model.dto.RanchDTO;
import com.company.trexshelter.model.entity.Breed;
import com.company.trexshelter.model.entity.Dog;
import com.company.trexshelter.model.entity.Ranch;
import com.company.trexshelter.repository.DogRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DogServiceImpl implements DogService {

    private DogRepository dogRepository;
    private ModelMapper modelMapper;

    @Autowired
    public DogServiceImpl(DogRepository dogRepository, ModelMapper modelMapper) {
        this.dogRepository = dogRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Dog> findAll() {
        List<Dog> dogs = dogRepository.findAll();
        if (dogs.size() == 0) {
            throw new DogException("The Dog entities are not exists.");
        }
        return dogs;
    }

    @Override
    public DogDTO findById(Long id) {
        Optional<Dog> optionalDog = dogRepository.findById(id);
        if (optionalDog.isPresent()) {
            Dog dog = optionalDog.get();
            DogDTO dogDTO = modelMapper.map(dog, DogDTO.class);
            dogDTO.setRanchDTO(modelMapper.map(dog.getRanch(), RanchDTO.class));
            dogDTO.setBreedDTO(modelMapper.map(dog.getBreed(), BreedDTO.class));
            return dogDTO;
        }
        throw new DogException("The Dog entity is not exists with id: " + id+".");
    }

    @Override
    public void deleteById(Long id) {
        try {
            dogRepository.deleteById(id);
        } catch (Exception exception) {
            throw new DogException("No Dog entity with id: " + id+".");
        }
    }

    @Override
    public DogDTO save(DogDTO dogDTO) {
        Dog dog=modelMapper.map(dogDTO,Dog.class);
        dog.setBreed(modelMapper.map(dogDTO.getBreedDTO(), Breed.class));
        dog.setRanch(modelMapper.map(dogDTO.getRanchDTO(), Ranch.class));
        System.out.println(dog);
         dogRepository.save(dog);
//        if(dog==response)
//        {
//            if(dogDTO.getId()==null)
//                dogDTO.setId(response.getId());
//        }
        return dogDTO;
    }
}
