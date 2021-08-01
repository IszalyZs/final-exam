package com.company.trexshelter.controller;

import com.company.trexshelter.model.dto.RanchDTO;
import com.company.trexshelter.service.RanchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(RanchController.class)
class RanchControllerUNIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RanchService ranchService;


    @Test
    void findAll() throws Exception {
        RanchDTO ranchDTO1 = new RanchDTO();
        ranchDTO1.setId(1L);
        ranchDTO1.setName("Trex Farm");
        ranchDTO1.setAddress("1111 Budapest Andrassy str.25.");
        RanchDTO ranchDTO2 = new RanchDTO();
        ranchDTO2.setId(2L);
        ranchDTO2.setName("Trex Farm");
        ranchDTO2.setAddress("2222 Szeged Anna str. 26.");
        when(ranchService.findAll()).thenReturn(Arrays.asList(ranchDTO1, ranchDTO2));
        mockMvc.perform(get("/ranch")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(ranchDTO1.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(ranchDTO1.getName())))
                .andExpect(jsonPath("$[0].address", is(ranchDTO1.getAddress())))
                .andExpect(jsonPath("$[1].id", is(ranchDTO2.getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(ranchDTO2.getName())))
                .andExpect(jsonPath("$[1].address", is(ranchDTO2.getAddress())));
    }

    @Test
    void findById() throws Exception {
        RanchDTO ranchDTO1 = new RanchDTO();
        ranchDTO1.setId(1L);
        ranchDTO1.setName("Trex Farm");
        ranchDTO1.setAddress("1111 Budapest Andrassy str.25.");
        RanchDTO ranchDTO2 = new RanchDTO();
        ranchDTO2.setId(2L);
        ranchDTO2.setName("Trex Farm");
        ranchDTO2.setAddress("2222 Szeged Anna str. 26.");
        when(ranchService.findById(2L)).thenReturn(ranchDTO2);
        mockMvc.perform(get("/ranch/2")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ranchDTO2.getId().intValue())))
                .andExpect(jsonPath("$.name", is(ranchDTO2.getName())))
                .andExpect(jsonPath("$.address", is(ranchDTO2.getAddress())));
    }

    @Test
    void findAllByRanchName() throws Exception {
        RanchDTO ranchDTO1 = new RanchDTO();
        ranchDTO1.setId(1L);
        ranchDTO1.setName("Trex Farm");
        ranchDTO1.setAddress("1111 Budapest Andrassy str.25.");
        RanchDTO ranchDTO2 = new RanchDTO();
        ranchDTO2.setId(2L);
        ranchDTO2.setName("Trex Hospital");
        ranchDTO2.setAddress("2222 Szeged Anna str. 26.");
        when(ranchService.findAllByRanchName("Trex Farm")).thenReturn(List.of(ranchDTO1));
        mockMvc.perform(get("/ranch/Trex Farm/ranch")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(ranchDTO1.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(ranchDTO1.getName())))
                .andExpect(jsonPath("$[0].address", is(ranchDTO1.getAddress())));
    }

    @Test
    void deleteById() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }
}