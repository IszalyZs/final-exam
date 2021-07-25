package com.company.trexshelter.integration;

import com.company.trexshelter.exception.DogException;
import com.company.trexshelter.model.entity.Breed;
import com.company.trexshelter.model.entity.Dog;
import com.company.trexshelter.model.entity.Gender;
import com.company.trexshelter.model.entity.Ranch;
import com.company.trexshelter.service.DogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class DogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DogService dogService;

    private Dog dogOne;

    private Dog dogTwo;

    @BeforeEach
    public void init() {
        Ranch ranch = new Ranch();
        ranch.setId(1L);
        ranch.setName("Dog Shelter");
        ranch.setAddress("1111 Bp. Váci str. 5.");

        Breed breedOne = new Breed();
        breedOne.setId(1L);
        breedOne.setName("pitbull");

        Breed breedTwo = new Breed();
        breedTwo.setId(1L);
        breedTwo.setName("pitbull");

        dogOne = new Dog();
        dogOne.setId(1L);
        dogOne.setAgeInMonth(11);
        dogOne.setChipCode("123456789012345");
        dogOne.setGender(Gender.MALE);
        dogOne.setBreed(breedOne);
        dogOne.setRanch(ranch);

        dogTwo = new Dog();
        dogTwo.setId(2L);
        dogTwo.setAgeInMonth(4);
        dogTwo.setChipCode("223456789012345");
        dogTwo.setGender(Gender.FEMALE);
        dogTwo.setBreed(breedTwo);
        dogTwo.setRanch(ranch);

    }

    @Test
    public void getAllDogs_ReturnAllDogs() throws Exception {
        when(dogService.findAll()).thenReturn(List.of(dogOne, dogTwo));
        mockMvc.perform(get("/dog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(dogOne.getId().intValue())))
                .andExpect(jsonPath("$[0].chipCode", is(dogOne.getChipCode())))
                .andExpect(jsonPath("$[0].ageInMonth", is(dogOne.getAgeInMonth())))
                .andExpect(jsonPath("$[0].gender", is(dogOne.getGender().toString())))
                .andExpect(jsonPath("$[0].breed.name", is(dogOne.getBreed().getName())))
                .andExpect(jsonPath("$[0].ranch.address", is(dogOne.getRanch().getAddress())))
                .andExpect(jsonPath("$[1].id", is(dogTwo.getId().intValue())))
                .andExpect(jsonPath("$[1].chipCode", is(dogTwo.getChipCode())))
                .andExpect(jsonPath("$[1].ageInMonth", is(dogTwo.getAgeInMonth())))
                .andExpect(jsonPath("$[1].gender", is(dogTwo.getGender().toString())))
                .andExpect(jsonPath("$[1].breed.name", is(dogTwo.getBreed().getName())))
                .andExpect(jsonPath("$[1].ranch.address", is(dogTwo.getRanch().getAddress())));
        verify(dogService, times(1)).findAll();
    }

    @Test
    public void findById_ReturnDog() throws Exception {
        when(dogService.findById(2L)).thenReturn(dogTwo);

        mockMvc.perform(get("/dog/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dogTwo.getId().intValue())))
                .andExpect(jsonPath("$.chipCode", is(dogTwo.getChipCode())))
                .andExpect(jsonPath("$.ageInMonth", is(dogTwo.getAgeInMonth())))
                .andExpect(jsonPath("$.gender", is(dogTwo.getGender().toString())))
                .andExpect(jsonPath("$.breed.name", is(dogTwo.getBreed().getName())))
                .andExpect(jsonPath("$.ranch.address", is(dogTwo.getRanch().getAddress())));
        verify(dogService, times(1)).findById(2L);
    }

    @Test
    public void findById_ReturnException() throws Exception {
        when(dogService.findById(3L)).thenThrow(DogException.class);

        mockMvc.perform(get("/dog/3"))
                .andExpect(status().isBadRequest());

        verify(dogService, times(1)).findById(3L);
    }

    @Test
    public void deleteById_DeleteDog() throws Exception {
        doNothing().when(dogService).deleteById(1L);
        mockMvc.perform(delete("/dog/1"))
                .andExpect(status().isOk());
        verify(dogService, times(1)).deleteById(1L);
    }

    @Test
    public void save_ReturnSavedDog() throws Exception {



    }






}
