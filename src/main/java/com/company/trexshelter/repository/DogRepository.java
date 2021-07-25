package com.company.trexshelter.repository;

import com.company.trexshelter.model.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    List<Dog> findDogsByChipCode(String chipCode);
    List<Dog> findDogsByBreed_Name(String name);
    List<Dog> findDogsByRanchId(Long ranchId);
    List<Dog> findDogsByBreed_NameAndRanch_Id(String breedsName, Long ranchId);

}
