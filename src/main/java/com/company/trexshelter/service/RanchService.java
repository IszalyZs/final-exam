package com.company.trexshelter.service;

import com.company.trexshelter.model.dto.RanchDTO;

import java.util.List;

public interface RanchService {
    List<RanchDTO> findAll();
    List<RanchDTO> findAllByRanchName(String name);
    RanchDTO findById(Long id);
    void deleteById(Long id);
    RanchDTO save(RanchDTO ranchDTO);
}
