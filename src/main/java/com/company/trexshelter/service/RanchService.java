package com.company.trexshelter.service;

import com.company.trexshelter.model.dto.RanchDTO;
import com.company.trexshelter.model.entity.Ranch;

import java.util.List;

public interface RanchService {
    List<RanchDTO> findAll();
    RanchDTO findById(Long id);
    void deleteById(Long id);
    void save(RanchDTO ranchDTO);
}
