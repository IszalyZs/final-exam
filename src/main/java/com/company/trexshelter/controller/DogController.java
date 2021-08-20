package com.company.trexshelter.controller;

import com.company.trexshelter.config.BindingErrorHandler;
import com.company.trexshelter.exception.DogException;
import com.company.trexshelter.model.dto.DogDTO;
import com.company.trexshelter.model.entity.Dog;
import com.company.trexshelter.service.DogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "Operations on dogs")
@RequestMapping("/dog")
public class DogController {
    private final DogService dogService;
    private final BindingErrorHandler bindingErrorHandler;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DogController(DogService dogService, BindingErrorHandler bindingErrorHandler) {
        this.dogService = dogService;
        this.bindingErrorHandler = bindingErrorHandler;
    }


    @GetMapping
    @Operation(summary = "list all dogs", description = "list all dogs")
    public ResponseEntity<List<Dog>> findAll() {
        return ResponseEntity.ok(dogService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "list dog by id", description = "list dog by id")
    public ResponseEntity<Dog> findById(@PathVariable("id") String id) {
        Long longId = verifyId(id);
        return ResponseEntity.ok(dogService.findById(longId));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "delete dog by id", description = "delete dog by id")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        Long longId = verifyId(id);
        dogService.deleteById(longId);
        return ResponseEntity.ok("The entity was deleted with id: " + id + "!");
    }

    @PostMapping
    @Operation(summary = "save dog", description = "save dog")
    public ResponseEntity<?> save(@Valid @RequestBody DogDTO dogDTO, BindingResult bindingResult) {
        if (dogDTO.getId() != null) dogDTO.setId(null);
        Dog response;
        String logMessage = "Posted dog entity contains error(s): ";
        bindingErrorHandler.bindingResult(bindingResult, logMessage, logger);
        try {
            response = dogService.save(dogDTO);
        } catch (DataIntegrityViolationException exc) {
            String message = "Duplicate entry at chip code:" + dogDTO.getChipCode() + " is already exists!";
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update dog by id", description = "update dog by id")
    public ResponseEntity<?> update(@Valid @RequestBody DogDTO dogDTO, BindingResult bindingResult, @PathVariable("id") String id) {
        Long longId = verifyId(id);
        Dog response;
        String logMessage = "Updated dog entity contains error(s): ";
        bindingErrorHandler.bindingResult(bindingResult, logMessage, logger);
        try {
            dogDTO.setId(longId);
            response = dogService.update(dogDTO);
        } catch (DataIntegrityViolationException exc) {
            String message = "Duplicate entry at chip code:" + dogDTO.getChipCode() + " is already exists!";
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/chip/{chipCode}")
    @Operation(summary = "list all dogs by chip code", description = "list all dogs by chip code")
    public ResponseEntity<List<Dog>> findAllByChipCode(@PathVariable("chipCode") String chipCode) {
        return ResponseEntity.ok(dogService.findAllByChipCode(chipCode));
    }

    @GetMapping("/breed/{breed}")
    @Operation(summary = "list all dogs by breed's name", description = "list all dogs by breed's name")
    public ResponseEntity<List<Dog>> findAllByBreedsName(@PathVariable("breed") String name) {
        return ResponseEntity.ok(dogService.findAllByBreedsName(name));
    }

    @GetMapping("/ranch/{id}")
    @Operation(summary = "list all dogs by ranch id", description = "list all dogs by ranch id")
    public ResponseEntity<List<Dog>> findAllByRanchsId(@PathVariable("id") String id) {
        Long longId = verifyId(id);
        return ResponseEntity.ok(dogService.findAllByRanchId(longId));
    }

    @GetMapping("/breed/{breed}/ranch/{id}")
    @Operation(summary = "list all dogs by breed's name and ranch id", description = "list all dogs by breed's name and ranch id")
    public ResponseEntity<List<Dog>> findAllByBreed_NameAndRanch_Id(@PathVariable("breed") String name, @PathVariable("id") String id) {
        Long longId = verifyId(id);
        return ResponseEntity.ok(dogService.findDogsByBreed_NameAndRanch_Id(name, longId));
    }

    private Long verifyId(String id) {
        Long longId;
        try {
            longId = Long.valueOf(id);
        } catch (Exception e) {
            String message = "You have to give a valid long id!";
            throw new DogException(message);
        }
        return longId;
    }
}
