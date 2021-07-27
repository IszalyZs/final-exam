package com.company.trexshelter.integration;

import com.company.trexshelter.model.dto.BreedDTO;
import com.company.trexshelter.model.dto.DogDTO;
import com.company.trexshelter.model.dto.RanchDTO;
import com.company.trexshelter.model.entity.Dog;
import com.company.trexshelter.model.entity.Gender;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DogControllerIT {
    @LocalServerPort
    private Integer port;

    private  String BASE_URL;

    private static TestRestTemplate restTemplate = new TestRestTemplate();

    private static List<RanchDTO> ranches = new ArrayList<>();

    private static List<BreedDTO> breeds = new ArrayList<>();

    private static List<DogDTO> dogs = new ArrayList<>();

    @BeforeAll
    public void init() {
        RanchDTO ranchOne = new RanchDTO();
        ranchOne.setId(1L);
        ranchOne.setName("Trex-Shelter");
        ranchOne.setAddress("1111 Budapest Andrassy str. 18.");
        RanchDTO ranchTwo = new RanchDTO();
        ranchTwo.setId(2L);
        ranchTwo.setName("Trex-Hospital");
        ranchTwo.setAddress("2222 Budapest NagySandor str. 11.");
        BreedDTO breedOne = new BreedDTO();
        breedOne.setId(1L);
        breedOne.setName("Akbash1");
        BreedDTO breedTwo = new BreedDTO();
        breedTwo.setId(2L);
        breedTwo.setName("Akita1");
        BreedDTO breedThree = new BreedDTO();
        breedThree.setId(3L);
        breedThree.setName("Pitbull1");
        DogDTO dogOne = new DogDTO();
        dogOne.setId(1L);
        dogOne.setChipCode("111111111111112");
        dogOne.setAgeInMonth(11);
        dogOne.setGender(Gender.MALE);
        dogOne.setBreedDTOId(breedOne.getId());
        dogOne.setRanchDTOId(ranchOne.getId());
        DogDTO dogTwo = new DogDTO();
        dogTwo.setId(2L);
        dogTwo.setChipCode("222222222222223");
        dogTwo.setAgeInMonth(1);
        dogTwo.setGender(Gender.MALE);
        dogTwo.setBreedDTOId(breedTwo.getId());
        dogTwo.setRanchDTOId(ranchOne.getId());
        DogDTO dogThree = new DogDTO();
        dogThree.setId(3L);
        dogThree.setChipCode("333333333333334");
        dogThree.setAgeInMonth(4);
        dogThree.setGender(Gender.FEMALE);
        dogThree.setBreedDTOId(breedOne.getId());
        dogThree.setRanchDTOId(ranchTwo.getId());
        DogDTO dogFour = new DogDTO();
        dogFour.setId(4L);
        dogFour.setChipCode("444444444444445");
        dogFour.setAgeInMonth(6);
        dogFour.setGender(Gender.MALE);
        dogFour.setBreedDTOId(breedThree.getId());
        dogFour.setRanchDTOId(ranchOne.getId());

        ranches.add(ranchOne);
        ranches.add(ranchTwo);
        breeds.add(breedOne);
        breeds.add(breedTwo);
        breeds.add(breedThree);
        dogs.add(dogOne);
        dogs.add(dogTwo);
        dogs.add(dogThree);
        dogs.add(dogFour);
        BASE_URL = "http://localhost:" + port;
        for (RanchDTO ranch : ranches) {
            restTemplate.postForObject(BASE_URL + "/ranch", new HttpEntity<>(ranch), RanchDTO.class);
        }
        for (BreedDTO breed : breeds) {
            restTemplate.postForObject(BASE_URL + "/breed", new HttpEntity<>(breed), BreedDTO.class);
        }


        for (DogDTO dog : dogs) {
            HttpEntity entity= new HttpEntity<>(dog);
            System.out.println(entity.getBody()+" pppppppppp");
            restTemplate.postForObject(BASE_URL + "/dog", entity, DogDTO.class);
        }

    }


    @Test
    public void findAll_shouldReturnListOfAllDogs() throws Exception {

        ResponseEntity<Dog[]> response = restTemplate.getForEntity(BASE_URL + "/dog", Dog[].class);
        List<Dog> actual = Arrays.asList(response.getBody());
        System.out.println(actual.size() + " " + dogs.size());
        Assertions.assertEquals(dogs.size(), actual.size());


    }

//    @Test
//    public void findById_ReturnDog() throws Exception {
//    }
//
//    @Test
//    public void findById_ReturnException() throws Exception {
//    }
//
//    @Test
//    public void deleteById_DeleteDog() throws Exception {
//    }
//
//    @Test
//    public void save_ReturnSavedDog() throws Exception {
//
//
//    }


}
