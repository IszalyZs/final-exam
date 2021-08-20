package com.company.trexshelter.controller;

import com.company.trexshelter.config.BindingErrorHandler;
import com.company.trexshelter.exception.RanchException;
import com.company.trexshelter.model.dto.RanchDTO;
import com.company.trexshelter.service.RanchService;
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

@RestController
@RequestMapping("/ranch")
@Tag(name = "Operations on ranches")
public class RanchController {

    private final BindingErrorHandler bindingErrorHandler;
    private final RanchService ranchService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RanchController(RanchService ranchService, BindingErrorHandler bindingErrorHandler) {
        this.ranchService = ranchService;
        this.bindingErrorHandler = bindingErrorHandler;
    }

    @GetMapping
    @Operation(summary = "list all ranches", description = "list all ranches")
    public ResponseEntity<List<RanchDTO>> findAll() {
        return ResponseEntity.ok(ranchService.findAll());
    }


    @GetMapping("/{id}")
    @Operation(summary = "list ranch by id", description = "list ranch by id")
    public ResponseEntity<RanchDTO> findById(@PathVariable("id") String id) {
        Long longId = verifyId(id);
        return ResponseEntity.ok(ranchService.findById(longId));
    }

    @GetMapping("/{name}/ranch")
    @Operation(summary = "list ranches by ranch's name", description = "list ranches by ranch's name")
    public ResponseEntity<List<RanchDTO>> findAllByRanchName(@PathVariable("name") String name) {
        return ResponseEntity.ok(ranchService.findAllByRanchName(name));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete ranch by id", description = "delete ranch by id")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        Long longId = verifyId(id);
        ranchService.deleteById(longId);
        return ResponseEntity.ok("The entity was deleted with id: " + id + "!");
    }

    @PostMapping
    @Operation(summary = "save ranch", description = "save ranch")
    public ResponseEntity<?> save(@Valid @RequestBody RanchDTO ranchDTO, BindingResult bindingResult) {
        if (ranchDTO.getId() != null) ranchDTO.setId(null);
        RanchDTO response;
        String logMessage = "Posted ranch entity contains error(s): ";
        bindingErrorHandler.bindingResult(bindingResult, logMessage, logger);
        try {
            response = ranchService.save(ranchDTO);
        } catch (DataIntegrityViolationException exc) {
            String message = "Duplicate entry at address:" + ranchDTO.getAddress() + " is already exists!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    @Operation(summary = "update ranch by id", description = "update ranch by id")
    public ResponseEntity<?> update(@Valid @RequestBody RanchDTO ranchDTO, BindingResult bindingResult, @PathVariable("id") String id) {
        Long longId = verifyId(id);
        RanchDTO response;
        String logMessage = "Updated ranch entity contains error(s): ";
        bindingErrorHandler.bindingResult(bindingResult, logMessage, logger);
        try {
            ranchDTO.setId(longId);
            response = ranchService.update(ranchDTO);
        } catch (DataIntegrityViolationException exc) {
            String message = "Duplicate entry at address:" + ranchDTO.getAddress() + " is already exists!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(response);
    }

    private Long verifyId(String id) {
        Long longId;
        try {
            longId = Long.valueOf(id);
        } catch (Exception e) {
            String message = "You have to give a valid long id!";
            throw new RanchException(message);
        }
        return longId;
    }
}
