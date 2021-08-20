package com.company.trexshelter.controller;

import com.company.trexshelter.exception.BreedException;
import com.company.trexshelter.model.dto.BreedDTO;
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

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(profiles = "testdb")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BreedControllerITTest {
    @Autowired
    private Flyway flyway;

    @Autowired
    private BreedController breedController;

    @LocalServerPort
    private Integer port;

    private String BASE_URL;

    private static TestRestTemplate restTemplate = new TestRestTemplate();

    private static List<BreedDTO> breeds = new ArrayList<>();

    @BeforeEach
    public void init() {
        BASE_URL = "http://localhost:" + port;
        flyway.clean();
        flyway.migrate();

        BreedDTO breedOne = new BreedDTO();
        breedOne.setId(1L);
        breedOne.setName("Akita");
        BreedDTO breedTwo = new BreedDTO();
        breedTwo.setId(2L);
        breedTwo.setName("Pitbull");
        BreedDTO breedThree = new BreedDTO();
        breedThree.setId(3L);
        breedThree.setName("Akbash");

        breeds.clear();
        breeds.add(breedOne);
        breeds.add(breedTwo);
        breeds.add(breedThree);

        for (BreedDTO breed : breeds) {
            restTemplate.postForObject(BASE_URL + "/breed", new HttpEntity<>(breed), BreedDTO.class);
        }
    }

    @Test
    void findAll_shouldReturnAllBreedDTO() {
        ResponseEntity<BreedDTO[]> response = restTemplate.getForEntity(BASE_URL + "/breed", BreedDTO[].class);
        List<BreedDTO> actual = Arrays.asList(response.getBody());
        List<BreedDTO> expected = breeds;
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, actual);
    }

    @Test
    void findById_inputValidId_shouldReturnRightBreedDTO() {
        Long id = 1L;
        ResponseEntity<BreedDTO> response = restTemplate.getForEntity(BASE_URL + "/breed/{id}", BreedDTO.class, id);
        BreedDTO expected = breeds.get(0);
        BreedDTO actual = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, actual);
    }

    @Test
    void findById_inputBadId_shouldReturnBadRequest() {
        String badId = "w";
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/breed/{id}", String.class, badId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteById_inputValidId_shouldReturnRightMessage() {
        Long id = 1L;
        ResponseEntity<BreedDTO> responseBeforeDelete = restTemplate.getForEntity(BASE_URL + "/breed/{id}", BreedDTO.class, id);
        assertEquals(HttpStatus.OK, responseBeforeDelete.getStatusCode());
        assertEquals(breeds.get(0), responseBeforeDelete.getBody());

        restTemplate.delete(BASE_URL + "/breed/{id}", id);
        ResponseEntity<String> responseAfterDelete = restTemplate.getForEntity(BASE_URL + "/breed/{id}", String.class, id);
        String expected = "The breed entity does not exist with id: " + id + "!";
        String actual = responseAfterDelete.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseAfterDelete.getStatusCode());
        assertEquals(expected, actual);
    }

    @Test
    void deleteById_inputBadId_shouldThrowBreedException() {
        String badId = "w";
        BreedException exception = assertThrows(BreedException.class, () -> breedController.deleteById(badId));
        String expected = "You have to give a valid long id!";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }


    @Test
    void save_inputBreedDTO_shouldReturnRightBreedDTO() {
        BreedDTO expected = new BreedDTO();
        expected.setName("Beagle");
        HttpEntity<BreedDTO> entity = new HttpEntity<>(expected);
        ResponseEntity<BreedDTO> response = restTemplate.postForEntity(BASE_URL + "/breed", entity, BreedDTO.class);
        BreedDTO actual = response.getBody();
        expected.setId(actual.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, actual);
    }

    @Test
    void save_inputDuplicateBreedsName_shouldReturnBadRequest1() {
        BreedDTO breedDTO = new BreedDTO();
        breedDTO.setName("Akita");
        HttpEntity<BreedDTO> entity = new HttpEntity<>(breedDTO);
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/breed", entity, String.class);
        String expected = "Duplicate entry at breed's name:" + breedDTO.getName() + " is already exists!";
        String actual = response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(expected, actual);
    }

    @Test
    void save_inputEmptyBreedsName_shouldReturnBadRequest2() {
        BreedDTO breedDTO = new BreedDTO();
        breedDTO.setName("");
        HttpEntity<BreedDTO> entity = new HttpEntity<>(breedDTO);
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/breed", entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void update_inputBreedDTO_shouldRetunRightBreedDTO() {
        Long id = 1L;
        BreedDTO expected = new BreedDTO();
        expected.setName("Beagle");
        HttpEntity<BreedDTO> entity = new HttpEntity<>(expected);
        restTemplate.put(BASE_URL + "/breed/{id}", entity, id);
        ResponseEntity<BreedDTO> response = restTemplate.getForEntity(BASE_URL + "/breed/{id}", BreedDTO.class, id);
        BreedDTO actual = response.getBody();
        expected.setId(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, actual);
    }
}