package com.company.trexshelter.integration;

import com.company.trexshelter.repository.DogRepository;
import com.company.trexshelter.service.DogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
public class DogControllerTest {
    private DogService dogService;
    private DogRepository dogRepository;

    @Autowired
    public DogControllerTest(DogService dogService, DogRepository dogRepository) {
        this.dogService = dogService;
        this.dogRepository = dogRepository;
    }

    @Test
    public void getAllDogs_ReturnAllDogs() throws Exception {

    }

    @Test
    public void findById_ReturnDog() throws Exception {
    }

    @Test
    public void findById_ReturnException() throws Exception {
    }

    @Test
    public void deleteById_DeleteDog() throws Exception {
    }

    @Test
    public void save_ReturnSavedDog() throws Exception {


    }


}
