package com.company.trexshelter.converter;

import com.company.trexshelter.model.dto.RanchDTO;
import com.company.trexshelter.model.entity.Ranch;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RanchToRanchDTO {
    private final ModelMapper modelMapper;

    @Autowired
    public RanchToRanchDTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RanchDTO getRanchDTO(Ranch ranch) {
        return modelMapper.map(ranch, RanchDTO.class);
    }
}
