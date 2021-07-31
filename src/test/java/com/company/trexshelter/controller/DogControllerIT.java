package com.company.trexshelter.controller;

import com.company.trexshelter.converter.DogToDogDTO;
import com.company.trexshelter.exception.DogException;
import com.company.trexshelter.model.dto.BreedDTO;
import com.company.trexshelter.model.dto.DogDTO;
import com.company.trexshelter.model.dto.RanchDTO;
import com.company.trexshelter.model.entity.Dog;
import com.company.trexshelter.model.entity.Gender;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles(profiles = "testdb")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DogControllerIT {
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
        dogTwo.setBreedDTOId(breedThree.getId());
        dogTwo.setRanchDTOId(ranchOne.getId());
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
    void findAll_shouldReturnAllDogs() {
        ResponseEntity<Dog[]> response = restTemplate.getForEntity(BASE_URL + "/dog", Dog[].class);
        List<Dog> actualDogs = Arrays.asList(response.getBody());
        List<DogDTO> actual = actualDogs.stream().map(dogToDogDTO::getDogDTO).collect(Collectors.toList());
        List<DogDTO> expected = dogs;
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, actual);
    }

    @Test
    void findById_inputValidId_shouldReturnRightDog() {
        String id = "2";
        ResponseEntity<Dog> response = restTemplate.getForEntity(BASE_URL + "/dog/{id}", Dog.class, id);
        DogDTO actual = dogToDogDTO.getDogDTO(response.getBody());
        DogDTO expected = dogs.get(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, actual);
    }

    @Test
    void findById_inputBadId_shouldReturnBadRequest() {
        String badId = "w";
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/dog/{id}", String.class, badId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    void deleteById_inputValidId_shouldReturnRightMessage() {
        String id = "1";
        ResponseEntity<Object> response = dogController.deleteById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String expected = "The entity was deleted with id: " + id + "!";
        String actual = (String) response.getBody();
        assertEquals(expected, actual);
    }

    @Test
    void deleteById_inputBadId_shouldReturnBadRequest() {
        String badId = "w";
        ResponseEntity<Object> response = dogController.deleteById(badId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void save_inputDogDTO_shouldReturnRightDog() {
        DogDTO expected = new DogDTO();
        expected.setChipCode("444444444444444");
        expected.setAgeInMonth(1);
        expected.setGender(Gender.FEMALE);
        expected.setBreedDTOId(2L);
        expected.setRanchDTOId(2L);
        HttpEntity<DogDTO> entity = new HttpEntity<>(expected);
        ResponseEntity<Dog> response = restTemplate.postForEntity(BASE_URL + "/dog", entity, Dog.class);
        DogDTO actual = dogToDogDTO.getDogDTO(response.getBody());
        expected.setId(actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    void save_inputDuplicateChipCode_shouldReturnBadRequest1() {
        DogDTO dogDTO = new DogDTO();
        dogDTO.setChipCode("111111111111111");
        dogDTO.setAgeInMonth(11);
        dogDTO.setGender(Gender.FEMALE);
        dogDTO.setBreedDTOId(2L);
        dogDTO.setRanchDTOId(2L);
        HttpEntity<DogDTO> entity = new HttpEntity<>(dogDTO);
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/dog", entity, String.class);
        String expected = "Duplicate entry at chip code:" + dogDTO.getChipCode() + " is already exists!";
        String actual = response.getBody();
        assertEquals(expected, actual);
    }

    @Test
    void save_inputBadBreedDTOId_shouldReturnBadRequest2() {
        DogDTO dogDTO = new DogDTO();
        dogDTO.setChipCode("666666666666666");
        dogDTO.setAgeInMonth(11);
        dogDTO.setGender(Gender.FEMALE);
        dogDTO.setBreedDTOId(8L);
        dogDTO.setRanchDTOId(2L);
        HttpEntity<DogDTO> entity = new HttpEntity<>(dogDTO);
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/dog", entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void save_inputBadAgeInMonth_shouldReturnBadRequest3() {
        DogDTO dogDTO = new DogDTO();
        dogDTO.setChipCode("666666666666666");
        dogDTO.setAgeInMonth(1111);
        dogDTO.setGender(Gender.FEMALE);
        dogDTO.setBreedDTOId(2L);
        dogDTO.setRanchDTOId(2L);
        HttpEntity<DogDTO> entity = new HttpEntity<>(dogDTO);
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/dog", entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void save_inputBadRanchDTOId_shouldReturnBadRequest4() {
        DogDTO dogDTO = new DogDTO();
        dogDTO.setChipCode("666666666666666");
        dogDTO.setAgeInMonth(11);
        dogDTO.setGender(Gender.FEMALE);
        dogDTO.setBreedDTOId(2L);
        dogDTO.setRanchDTOId(8L);
        HttpEntity<DogDTO> entity = new HttpEntity<>(dogDTO);
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/dog", entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void update_inputDogDTO_shouldRetunDog() {
        DogDTO expected = new DogDTO();
        expected.setId(1L);
        expected.setChipCode("444444444444444");
        expected.setAgeInMonth(1);
        expected.setGender(Gender.FEMALE);
        expected.setBreedDTOId(2L);
        expected.setRanchDTOId(2L);
        HttpEntity<DogDTO> entity = new HttpEntity<>(expected);
        ResponseEntity<Dog> response = restTemplate.postForEntity(BASE_URL + "/dog", entity, Dog.class);
        DogDTO actual = dogToDogDTO.getDogDTO(response.getBody());
        assertEquals(expected, actual);
    }

    @Test
    void update_inputDuplicateChipCode_shouldReturnBadRequest1() {
        DogDTO dogDTO = new DogDTO();
        dogDTO.setId(1L);
        dogDTO.setChipCode("222222222222222");
        dogDTO.setAgeInMonth(11);
        dogDTO.setGender(Gender.FEMALE);
        dogDTO.setBreedDTOId(2L);
        dogDTO.setRanchDTOId(2L);
        HttpEntity<DogDTO> entity = new HttpEntity<>(dogDTO);
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/dog", entity, String.class);
        String expected = "Duplicate entry at chip code:" + dogDTO.getChipCode() + " is already exists!";
        String actual = response.getBody();
        assertEquals(expected, actual);
    }

    @Test
    void update_inputBadBreedDTOId_shouldReturnBadRequest2() {
        DogDTO dogDTO = new DogDTO();
        dogDTO.setId(1L);
        dogDTO.setChipCode("666666666666666");
        dogDTO.setAgeInMonth(11);
        dogDTO.setGender(Gender.FEMALE);
        dogDTO.setBreedDTOId(8L);
        dogDTO.setRanchDTOId(2L);
        HttpEntity<DogDTO> entity = new HttpEntity<>(dogDTO);
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/dog", entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void update_inputBadAgeInMonth_shouldReturnBadRequest3() {
        DogDTO dogDTO = new DogDTO();
        dogDTO.setId(1L);
        dogDTO.setChipCode("666666666666666");
        dogDTO.setAgeInMonth(1111);
        dogDTO.setGender(Gender.FEMALE);
        dogDTO.setBreedDTOId(2L);
        dogDTO.setRanchDTOId(2L);
        HttpEntity<DogDTO> entity = new HttpEntity<>(dogDTO);
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/dog", entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void update_inputBadRanchDTOId_shouldReturnBadRequest4() {
        DogDTO dogDTO = new DogDTO();
        dogDTO.setId(1L);
        dogDTO.setChipCode("666666666666666");
        dogDTO.setAgeInMonth(11);
        dogDTO.setGender(Gender.FEMALE);
        dogDTO.setBreedDTOId(2L);
        dogDTO.setRanchDTOId(8L);
        HttpEntity<DogDTO> entity = new HttpEntity<>(dogDTO);
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/dog", entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void findAllByChipCode_inputValidChipCode_shouldReturnAllDogs() {
        String chipCode = "222222222222222";
        ResponseEntity<Dog[]> response = restTemplate.getForEntity(BASE_URL + "/dog/chip/{chipCode}", Dog[].class, chipCode);
        List<Dog> actualDogs = Arrays.asList(response.getBody());
        List<DogDTO> actual = actualDogs.stream().map(dogToDogDTO::getDogDTO).collect(Collectors.toList());
        List<DogDTO> expected = List.of(dogs.get(1));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, actual);
    }

    @Test
    void findAllByChipCode_inputBadChipCode_throwDogException() {
        String badChipCode = "w";
        assertThrows(DogException.class, () -> dogController.findAllByChipCode(badChipCode));
    }


    @Test
    void findAllByBreedsName_inputValidBreedsName_shouldReturnAllDogs() {
        String breedsName = "Akbash";
        ResponseEntity<Dog[]> response = restTemplate.getForEntity(BASE_URL + "/dog/breed/{breed}", Dog[].class, breedsName);
        List<Dog> actualDogs = Arrays.asList(response.getBody());
        List<DogDTO> actual = actualDogs.stream().map(dogToDogDTO::getDogDTO).collect(Collectors.toList());
        List<DogDTO> expected = List.of(dogs.get(1), dogs.get(2));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, actual);
    }

    @Test
    void findAllByBreedsName_inputBadBreedsName_throwDogException() {
        String badBreedsName = "w";
        assertThrows(DogException.class, () -> dogController.findAllByBreedsName(badBreedsName));
    }

    @Test
    void findAllByRanchsId_inputValidId_shouldReturnAllDogs() {
        String id = "1";
        ResponseEntity<Dog[]> response = restTemplate.getForEntity(BASE_URL + "/dog/ranch/{id}", Dog[].class, id);
        List<Dog> actualDogs = Arrays.asList(response.getBody());
        List<DogDTO> actual = actualDogs.stream().map(dogToDogDTO::getDogDTO).collect(Collectors.toList());
        List<DogDTO> expected = List.of(dogs.get(0), dogs.get(1));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, actual);
    }

    @Test
    void findAllByRanchsId_inputBadId_shouldReturnAllDogs() {
        String badId = "w";
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/dog/ranch/{id}", String.class, badId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void findAllByBreed_allInputsValid_shouldReturnAllDogs() {
        String breedsName = "Akbash";
        String ranchId = "1";
        ResponseEntity<Dog[]> response = restTemplate.getForEntity(BASE_URL + "/dog/breed/{breed}/ranch/{id}", Dog[].class, breedsName, ranchId);
        List<Dog> actualDogs = Arrays.asList(response.getBody());
        List<DogDTO> actual = actualDogs.stream().map(dogToDogDTO::getDogDTO).collect(Collectors.toList());
        List<DogDTO> expected = List.of(dogs.get(1));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, actual);
    }

    @Test
    void findAllByBreed_badInputs_shouldReturnBadRequest() {
        String breedsName = "Akbash1";
        String ranchId = "w";
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/dog/breed/{breed}/ranch/{id}", String.class, breedsName, ranchId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}