package com.company.trexshelter.controller;

import com.company.trexshelter.exception.DogException;
import com.company.trexshelter.model.dto.DogDTO;
import com.company.trexshelter.model.entity.Dog;
import com.company.trexshelter.service.DogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/dog")
public class DogController {
    private DogService dogService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DogController(DogService dogService) {
        this.dogService = dogService;
    }


    @GetMapping
    public List<Dog> getAllDogs() {
        return dogService.findAll();
    }


    @GetMapping("/{id}")
    public DogDTO findById(@PathVariable("id") String id) {
        Long longId;
        try {
            longId = Long.valueOf(id);
        } catch (Exception e) {
            throw new DogException("You have to give a valid long id.");
        }
        return dogService.findById(longId);
    }

    @DeleteMapping
    public void deleteThrowException() {
        throw new DogException("You have to give a valid long id.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        Long longId;
        try {
            longId = Long.valueOf(id);
        } catch (Exception e) {
            throw new DogException("You have to give a valid long id.");
        }
        dogService.deleteById(longId);
        return ResponseEntity.ok("The entity was deleted with id: " + id + ".");
    }

    @PostMapping
    public DogDTO save(@Valid @RequestBody DogDTO dogDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("Posted DogDTO entity contains error(s): " + bindingResult.getErrorCount());
            bindingResult.getAllErrors().forEach(error -> {
                String message = error.getObjectName() + " " + error.getCode() + " " + error.getDefaultMessage();
                logger.error(message);
            });
            throw new DogException("");
        }
        return dogService.save(dogDTO);
    }
}
