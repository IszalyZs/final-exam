package com.company.trexshelter.converter;

import com.company.trexshelter.model.dto.RanchDTO;
import com.company.trexshelter.model.entity.Ranch;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RanchDTOToRanch {
    private final ModelMapper modelMapper;

    @Autowired
    public RanchDTOToRanch(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Ranch getRanch(RanchDTO ranchDTO) {
        return modelMapper.map(ranchDTO, Ranch.class);
    }


}
