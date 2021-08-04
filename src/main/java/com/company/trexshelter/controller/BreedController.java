package com.company.trexshelter.controller;

import com.company.trexshelter.model.dto.BreedDTO;
import com.company.trexshelter.service.BreedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/breed")
@Tag(name = "Operations on breeds")
public class BreedController {

    private final BreedService breedService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public BreedController(BreedService breedService) {
        this.breedService = breedService;
    }


    @GetMapping
    @Operation(summary = "list all breeds", description = "list all breeds")
    public ResponseEntity<List<BreedDTO>> findAll() {
        return ResponseEntity.ok(breedService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "list breed by id", description = "list breed by id")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        Long longId;
        try {
            longId = Long.valueOf(id);
        } catch (Exception e) {
            String message = "You have to give a valid long id!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(breedService.findById(longId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete breed by id", description = "delete breed by id")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        Long longId;
        try {
            longId = Long.valueOf(id);
        } catch (Exception e) {
            String message = "You have to give a valid long id!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        breedService.deleteById(longId);
        return ResponseEntity.ok("The entity was deleted with id: " + id + "!");
    }

    @PostMapping
    @Operation(summary = "save breed", description = "save breed")
    public ResponseEntity<?> save(@Valid @RequestBody BreedDTO breedDTO, BindingResult bindingResult) {
        BreedDTO response;
        AtomicReference<String> sumMessage = new AtomicReference<>("");
        if (bindingResult.hasErrors()) {
            logger.error("Posted breed entity contains error(s): " + bindingResult.getErrorCount());
            bindingResult.getAllErrors().forEach(error -> {
                String message = "Object name:" + error.getObjectName() + ", error code:" + error.getCode() + ", error message:" + error.getDefaultMessage();
                logger.error(message);
                sumMessage.set(sumMessage + message + "\n");
            });
            return new ResponseEntity<>(sumMessage.get(), HttpStatus.BAD_REQUEST);
        }
        try {
            response = breedService.save(breedDTO);
        } catch (DataIntegrityViolationException exc) {
            String message = "Duplicate entry at breed's name:" + breedDTO.getName() + " is already exists!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update breed by id", description = "update breed by id")
    public ResponseEntity<?> update(@Valid @RequestBody BreedDTO breedDTO, BindingResult bindingResult, @PathVariable("id") String id) {
        Long longId;
        try {
            longId = Long.valueOf(id);
        } catch (Exception e) {
            String message = "You have to give a valid long id!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        BreedDTO response;
        AtomicReference<String> sumMessage = new AtomicReference<>("");
        if (bindingResult.hasErrors()) {
            logger.error("Posted breed entity contains error(s): " + bindingResult.getErrorCount());
            bindingResult.getAllErrors().forEach(error -> {
                String message = "Object name:" + error.getObjectName() + ", error code:" + error.getCode() + ", error message:" + error.getDefaultMessage();
                logger.error(message);
                sumMessage.set(sumMessage + message + "\n");
            });
            return new ResponseEntity<>(sumMessage.get(), HttpStatus.BAD_REQUEST);
        }
        try {
            breedDTO.setId(longId);
            response = breedService.save(breedDTO);
        } catch (DataIntegrityViolationException exc) {
            String message = "Duplicate entry at breed's name:" + breedDTO.getName() + " is already exists!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(response);
    }
}
