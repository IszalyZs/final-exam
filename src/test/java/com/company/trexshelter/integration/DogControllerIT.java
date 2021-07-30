package com.company.trexshelter.integration;

import com.company.trexshelter.controller.DogController;
import com.company.trexshelter.converter.DogToDogDTO;
import com.company.trexshelter.exception.DogException;
import com.company.trexshelter.model.dto.BreedDTO;
import com.company.trexshelter.model.dto.DogDTO;
import com.company.trexshelter.model.dto.RanchDTO;
import com.company.trexshelter.model.entity.Dog;
import com.company.trexshelter.model.entity.Gender;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ActiveProfiles(profiles = "testdb")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DogControllerIT {
    @Autowired
    private Flyway flyway;

    @Autowired
    private DogToDogDTO dogToDogDTO;

    @Autowired
    private DogController dogController;

    @LocalServerPort
    private Integer port;

    private String BASE_URL;

    private static TestRestTemplate restTemplate = new TestRestTemplate();

    private static List<RanchDTO> ranches = new ArrayList<>();

    private static List<BreedDTO> breeds = new ArrayList<>();

    private static List<DogDTO> dogs = new ArrayList<>();

    @BeforeEach
    public void init() {
        BASE_URL = "http://localhost:" + port;
        flyway.clean();
        flyway.migrate();
        RanchDTO ranchOne = new RanchDTO();
        ranchOne.setId(1L);
        ranchOne.setName("Trex Farm");
        ranchOne.setAddress("1111 Budapest Andrassy str.25.");
        RanchDTO ranchTwo = new RanchDTO();
        ranchTwo.setId(2L);
        ranchTwo.setName("Trex Farm");
        ranchTwo.setAddress("2222 Szeged Anna str. 26.");
        RanchDTO ranchThree = new RanchDTO();
        ranchThree.setId(3L);
        ranchThree.setName("Trex Hospital");
        ranchThree.setAddress("3333 Eger Balazs str 5.");

        BreedDTO breedOne = new BreedDTO();
        breedOne.setId(1L);
        breedOne.setName("Akita");
        BreedDTO breedTwo = new BreedDTO();
        breedTwo.setId(2L);
        breedTwo.setName("Pitbull");
        BreedDTO breedThree = new BreedDTO();
        breedThree.setId(3L);
        breedThree.setName("Akbash");
        DogDTO dogOne = new DogDTO();
        dogOne.setId(1L);
        dogOne.setChipCode("111111111111111");
        dogOne.setAgeInMonth(11);
        dogOne.setGender(Gender.MALE);
        dogOne.setBreedDTOId(breedOne.getId());
        dogOne.setRanchDTOId(ranchOne.getId());
        DogDTO dogTwo = new DogDTO();
        dogTwo.setId(2L);
        dogTwo.setChipCode("222222222222222");
        dogTwo.setAgeInMonth(1);
        dogTwo.setGender(Gender.FEMALE);
        dogTwo.setBreedDTOId(breedTwo.getId());
        dogTwo.setRanchDTOId(ranchTwo.getId());
        DogDTO dogThree = new DogDTO();
        dogThree.setId(3L);
        dogThree.setChipCode("333333333333333");
        dogThree.setAgeInMonth(12);
        dogThree.setGender(Gender.MALE);
        dogThree.setBreedDTOId(breedThree.getId());
        dogThree.setRanchDTOId(ranchThree.getId());
        ranches.clear();
        breeds.clear();
        dogs.clear();
        ranches.add(ranchOne);
        ranches.add(ranchTwo);
        ranches.add(ranchThree);
        breeds.add(breedOne);
        breeds.add(breedTwo);
        breeds.add(breedThree);
        dogs.add(dogOne);
        dogs.add(dogTwo);
        dogs.add(dogThree);

        for (RanchDTO ranch : ranches) {
            restTemplate.postForObject(BASE_URL + "/ranch", new HttpEntity<>(ranch), RanchDTO.class);
        }
        for (BreedDTO breed : breeds) {
            restTemplate.postForObject(BASE_URL + "/breed", new HttpEntity<>(breed), BreedDTO.class);
        }
        for (DogDTO dog : dogs) {
            restTemplate.postForObject(BASE_URL + "/dog", new HttpEntity<>(dog), Dog.class);
        }
    }


    @Test
    public void findAll_shouldReturnListOfAllDogs() {
//        ResponseEntity<Dog[]> response = restTemplate.getForEntity(BASE_URL + "/dog", Dog[].class);
//        List<Dog> actualDogs = Arrays.asList(response.getBody());
//        List<DogDTO> actualDogDTOs = actualDogs.stream().map(dogToDogDTO::getDogDTO).collect(Collectors.toList());
//        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
//        Assertions.assertEquals(dogs, actualDogDTOs);
        ResponseEntity<List<Dog>> response = dogController.findAll();
        List<Dog> actualDogs = response.getBody();
        List<DogDTO> actualDogDTOs = actualDogs.stream().map(dogToDogDTO::getDogDTO).collect(Collectors.toList());
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(dogs, actualDogDTOs);


    }

    @Test
    public void findById_returnRightDog(){
        String id="2";
        ResponseEntity<Dog> response = restTemplate.getForEntity(BASE_URL + "/dog/{id}", Dog.class, id);
        DogDTO actual = dogToDogDTO.getDogDTO(response.getBody());
        DogDTO expected = dogs.get(1);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(expected,actual);
    }
    @Test
    public void deleteById_throw(){
        String id="1";
        restTemplate.delete(BASE_URL + "/dog/{id}", id);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,restTemplate.getForEntity(BASE_URL+"/dog/{id}",DogException.class,id).getStatusCode());

    }

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
