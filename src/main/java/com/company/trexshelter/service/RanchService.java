package com.company.trexshelter.service;

import com.company.trexshelter.exception.RanchException;
import com.company.trexshelter.converter.RanchDTOToRanch;
import com.company.trexshelter.converter.RanchToRanchDTO;
import com.company.trexshelter.model.dto.RanchDTO;
import com.company.trexshelter.model.entity.Ranch;
import com.company.trexshelter.repository.RanchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RanchService {
    private final RanchRepository ranchRepository;
    private final RanchDTOToRanch ranchDTOToRanch;
    private final RanchToRanchDTO ranchToRanchDTO;

    @Autowired
    public RanchService(RanchRepository ranchRepository, RanchDTOToRanch ranchDTOToRanch, RanchToRanchDTO ranchToRanchDTO) {
        this.ranchRepository = ranchRepository;
        this.ranchDTOToRanch = ranchDTOToRanch;
        this.ranchToRanchDTO = ranchToRanchDTO;
    }

    public List<RanchDTO> findAll() {
        List<Ranch> ranches = ranchRepository.findAll();
        if (ranches.size() == 0) {
            throw new RanchException("The ranch entities do not exist!");
        }
        return ranches.stream().map(ranchToRanchDTO::getRanchDTO).collect(Collectors.toList());
    }

    public List<RanchDTO> findAllByRanchName(String name) {
        List<Ranch> ranches = ranchRepository.findAllByName(name);
        if (ranches.size() == 0) {
            throw new RanchException("The ranch entities do not exist!");
        }
        return ranches.stream().map(ranchToRanchDTO::getRanchDTO).collect(Collectors.toList());
    }

    public RanchDTO findById(Long id) {
        Optional<Ranch> optionalRanch = ranchRepository.findById(id);
        if (optionalRanch.isPresent()) {
            Ranch ranch = optionalRanch.get();
            return ranchToRanchDTO.getRanchDTO(ranch);
        }
        throw new RanchException("The ranch entity does not exist with id: " + id + "!");
    }

    public void deleteById(Long id) {
        try {
            ranchRepository.deleteById(id);
        } catch (Exception exception) {
            throw new RanchException("No ranch entity with id: " + id + "!");
        }
    }

    public RanchDTO save(RanchDTO ranchDTO) {
        Ranch ranch = ranchRepository.save(ranchDTOToRanch.getRanch(ranchDTO));
        return ranchToRanchDTO.getRanchDTO(ranch);
    }

    public RanchDTO update(RanchDTO ranchDTO) {
        Ranch ranch = ranchRepository.save(ranchDTOToRanch.getRanch(ranchDTO));
        return ranchToRanchDTO.getRanchDTO(ranch);
    }
}
