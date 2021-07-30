package com.company.trexshelter.controller;

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
import java.util.concurrent.atomic.AtomicReference;

@RestController
@Tag(name = "Operations on dogs")
@RequestMapping("/dog")
public class DogController {
    private final DogService dogService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DogController(DogService dogService) {
        this.dogService = dogService;
    }


    @GetMapping
    @Operation(summary = "list all dogs", description = "list all dogs")
    public ResponseEntity<List<Dog>> findAll() {
        return ResponseEntity.ok(dogService.findAll());
    }


    @GetMapping("/{id}")
    @Operation(summary = "list dog by id", description = "list dog by id")
    public ResponseEntity<Dog> findById(@PathVariable("id") String id) {
        Long longId;
        try {
            longId = Long.valueOf(id);
        } catch (Exception e) {
            throw new DogException("You have to give a valid long id!");
        }
        return ResponseEntity.ok(dogService.findById(longId));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "delete dog by id", description = "delete dog by id")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        Long longId;
        try {
            longId = Long.valueOf(id);
        } catch (Exception e) {
            throw new DogException("You have to give a valid long id!");
        }
        dogService.deleteById(longId);
        return ResponseEntity.ok("The entity was deleted with id: " + id + "!");
    }

    @PostMapping
    @Operation(summary = "save dog", description = "save dog")
    public ResponseEntity<Dog> save(@Valid @RequestBody DogDTO dogDTO, BindingResult bindingResult) {
        Dog response;
        AtomicReference<String> sumMessage = new AtomicReference<>("");
        if (bindingResult.hasErrors()) {
            logger.error("Posted dog entity contains error(s): " + bindingResult.getErrorCount());
            bindingResult.getAllErrors().forEach(error -> {
                String message = "Object name:" + error.getObjectName() + ", error code:" + error.getCode() + ", error message:" + error.getDefaultMessage();
                logger.error(message);
                sumMessage.set(sumMessage + message + "\n");
            });
            throw new DogException(sumMessage.get());
        }
        try {
            response = dogService.save(dogDTO);
        } catch (DataIntegrityViolationException exc) {
            throw new DogException("Duplicate entry at chip code:" + dogDTO.getChipCode() + " is already exists!");
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update dog by id", description = "update dog by id")
    public ResponseEntity<Dog> update(@Valid @RequestBody DogDTO dogDTO, BindingResult bindingResult, @PathVariable("id") String id) {
        Long longId;
        try {
            longId = Long.valueOf(id);
        } catch (Exception e) {
            throw new DogException("You have to give a valid long id!");
        }
        Dog response;
        AtomicReference<String> sumMessage = new AtomicReference<>("");
        if (bindingResult.hasErrors()) {
            logger.error("Posted dog entity contains error(s): " + bindingResult.getErrorCount());
            bindingResult.getAllErrors().forEach(error -> {
                String message = "Object name:" + error.getObjectName() + ", error code:" + error.getCode() + ", error message:" + error.getDefaultMessage();
                logger.error(message);
                sumMessage.set(sumMessage + message + "\n");
            });
            throw new DogException(sumMessage.get());
        }
        try {
            dogDTO.setId(longId);
            response = dogService.save(dogDTO);
        } catch (DataIntegrityViolationException exc) {
            throw new DogException("Duplicate entry at chip code:" + dogDTO.getChipCode() + " is already exists!");
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
        Long longId;
        try {
            longId = Long.valueOf(id);
        } catch (Exception e) {
            throw new DogException("You have to give a valid long id!");
        }
        return ResponseEntity.ok(dogService.findAllByRanchId(longId));
    }

    @GetMapping("/breed/{breed}/ranch/{id}")
    @Operation(summary = "list all dogs by breed's name and ranch id", description = "list all dogs by breed's name and ranch id")
    public ResponseEntity<List<Dog>> findAllByBreed_NameAndRanch_Id(@PathVariable("breed") String name, @PathVariable("id") String id) {
        Long longId;
        try {
            longId = Long.valueOf(id);
        } catch (Exception e) {
            throw new DogException("You have to give a valid long id!");
        }
        return ResponseEntity.ok(dogService.findDogsByBreed_NameAndRanch_Id(name, longId));
    }


}
