package com.company.trexshelter.controller;

import com.company.trexshelter.model.dto.RanchDTO;
import com.company.trexshelter.service.RanchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.Arrays;
import java.util.List;

@WebMvcTest(RanchController.class)
class RanchControllerUNIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RanchService ranchService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findAll_shouldReturnAllRanchDTOs() throws Exception {
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
        verify(ranchService, times(1))
                .findAll();

    }

    @Test
    void findById_inputValidId_shouldReturnRightRanchDTO() throws Exception {
        RanchDTO ranchDTO1 = new RanchDTO();
        ranchDTO1.setId(1L);
        ranchDTO1.setName("Trex Farm");
        ranchDTO1.setAddress("1111 Budapest Andrassy str.25.");
        RanchDTO ranchDTO2 = new RanchDTO();
        ranchDTO2.setId(2L);
        ranchDTO2.setName("Trex Farm");
        ranchDTO2.setAddress("2222 Szeged Anna str. 26.");
        when(ranchService.findById(anyLong())).thenReturn(ranchDTO2);
        Long id = 2L;
        mockMvc.perform(get("/ranch/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ranchDTO2.getId().intValue())))
                .andExpect(jsonPath("$.name", is(ranchDTO2.getName())))
                .andExpect(jsonPath("$.address", is(ranchDTO2.getAddress())));

        verify(ranchService, times(1))
                .findById(id);
    }

    @Test
    void findAllByRanchName_inputValidRanchName_shouldReturnRightRanches() throws Exception {
        RanchDTO ranchDTO1 = new RanchDTO();
        ranchDTO1.setId(1L);
        ranchDTO1.setName("Trex Farm");
        ranchDTO1.setAddress("1111 Budapest Andrassy str.25.");
        RanchDTO ranchDTO2 = new RanchDTO();
        ranchDTO2.setId(2L);
        ranchDTO2.setName("Trex Hospital");
        ranchDTO2.setAddress("2222 Szeged Anna str. 26.");
        String name = "Trex Farm";
        when(ranchService.findAllByRanchName(anyString())).thenReturn(List.of(ranchDTO1));
        mockMvc.perform(get("/ranch/{name}/ranch", name)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(ranchDTO1.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(ranchDTO1.getName())))
                .andExpect(jsonPath("$[0].address", is(ranchDTO1.getAddress())));

        verify(ranchService, times(1))
                .findAllByRanchName(anyString());

    }

    @Test
    void deleteById_inputValidId() throws Exception {
        RanchDTO ranchDTO1 = new RanchDTO();
        ranchDTO1.setId(1L);
        ranchDTO1.setName("Trex Farm");
        ranchDTO1.setAddress("1111 Budapest Andrassy str.25.");
        mockMvc.perform(delete("/ranch/{id}", anyLong()))
                .andExpect(status().isOk());
        verify(ranchService, times(1))
                .deleteById(anyLong());
    }

    @Test
    void save_inputRightRanchDTO_shouldRanchDTO() throws Exception {
        RanchDTO ranchDTO = new RanchDTO();
        ranchDTO.setName("Trex Farm");
        ranchDTO.setAddress("1111 Budapest Andrassy str.25.");
        when(ranchService.save(any(RanchDTO.class))).thenReturn(ranchDTO);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/ranch")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(ranchDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Trex Farm")))
                .andExpect(jsonPath("$.address", equalTo("1111 Budapest Andrassy str.25.")));

        verify(ranchService, times(1))
                .save(any(RanchDTO.class));
    }

}