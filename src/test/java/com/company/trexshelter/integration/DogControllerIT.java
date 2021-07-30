package com.company.trexshelter.integration;

import com.company.trexshelter.model.dto.BreedDTO;
import com.company.trexshelter.model.dto.DogDTO;
import com.company.trexshelter.model.dto.RanchDTO;
import com.company.trexshelter.model.entity.Breed;
import com.company.trexshelter.model.entity.Dog;
import com.company.trexshelter.model.entity.Gender;
import com.company.trexshelter.model.entity.Ranch;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ActiveProfiles(profiles = "testdb")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DogControllerIT {
    @Autowired
    Flyway flyway;

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
    public void findAll_shouldReturnListOfAllDogs() throws Exception {
        ResponseEntity<Dog[]> response = restTemplate.getForEntity(BASE_URL + "/dog", Dog[].class);
        List<Dog> actual = Arrays.asList(response.getBody());
        List<DogDTO> expected = dogs;
        expected.forEach(System.out::println);
        
        actual.forEach(System.out::println);
        Assertions.assertEquals(expected.size(), actual.size());
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
