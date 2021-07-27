package com.company.trexshelter.controller;

import com.company.trexshelter.exception.DogException;
import com.company.trexshelter.converter.DogToDogDTO;
import com.company.trexshelter.model.dto.DogDTO;
import com.company.trexshelter.model.entity.Dog;
import com.company.trexshelter.service.BreedService;
import com.company.trexshelter.service.DogService;
import com.company.trexshelter.service.RanchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@Tag(name="Operations on dogs")
@RequestMapping("/dog")
public class DogController {
    private final DogService dogService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RanchService ranchService;
    private final BreedService breedService;
    private final DogToDogDTO dogToDogDTO;

    @Autowired
    public DogController(DogService dogService, RanchService ranchService, BreedService breedService, DogToDogDTO dogToDogDTO) {
        this.dogService = dogService;
        this.ranchService = ranchService;
        this.breedService = breedService;
        this.dogToDogDTO = dogToDogDTO;
    }

    @GetMapping("/d")
    public String getDogPage(Model model) {
        DogDTO dogDTO = new DogDTO();
        dogDTO.setBreedDTOs(breedService.findAll());
        dogDTO.setRanchDTOS(ranchService.findAll());
        model.addAttribute("dogDTO", dogDTO);
        return "dog";
    }


    @GetMapping("/all/{id}")
    public String getDogPage(@PathVariable("id") Long id, Model model) {
        Dog dog = dogService.findById(id);
        DogDTO dogDTO = dogToDogDTO.getDogDTO(dog);
        dogDTO.setBreedDTOs(breedService.findAll());
        dogDTO.setRanchDTOS(ranchService.findAll());
        model.addAttribute("dogDTO", dogDTO);
        return "dog";
    }

    @PostMapping("/save")
    public String saveDogDTO(@Valid @ModelAttribute DogDTO dogDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            logger.error("Posted Dog entity contains error(s): " + bindingResult.getErrorCount());
            bindingResult.getAllErrors().forEach(error -> {
                String message = error.getObjectName() + " " + error.getCode() + " " + error.getDefaultMessage();
                logger.error(message);
            });
            dogDTO.setBreedDTOs(breedService.findAll());
            dogDTO.setRanchDTOS(ranchService.findAll());
            return "dog";
        }
        try {
            dogService.save(dogDTO);
        } catch (DataIntegrityViolationException exc) {
            redirectAttributes.addFlashAttribute("errors", "Duplicate entry " + dogDTO.getChipCode() + " is already exists!");
            return "redirect:/dog/d";
        }
        return "redirect:/dog/d";
    }


    @GetMapping
    @Operation(summary = "list all dogs",description = "list all dogs")
    @ResponseBody
    public ResponseEntity<List<Dog>> findAll() {
        return ResponseEntity.ok(dogService.findAll());
    }


    @GetMapping("/{id}")
    @Operation(summary = "list dog by id",description ="list dog by id" )
    @ResponseBody
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
    @Operation(summary = "delete dog by id",description = "delete dog by id")
    @ResponseBody
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
    @Operation(summary = "save dog",description ="save dog" )
    @ResponseBody
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

    @GetMapping("/chip/{chipCode}")
    @Operation(summary = "list all dogs by chip code",description ="list all dogs by chip code" )
    @ResponseBody
    public ResponseEntity<List<Dog>> findAllByChipCode(@PathVariable("chipCode") String chipCode) {
        return ResponseEntity.ok(dogService.findAllByChipCode(chipCode));
    }

    @GetMapping("/breed/{breed}")
    @Operation(summary = "list all dogs by breed's name",description ="list all dogs by breed's name" )
    @ResponseBody
    public ResponseEntity<List<Dog>> findAllByBreedsName(@PathVariable("breed") String name) {
        return ResponseEntity.ok(dogService.findAllByBreedsName(name));
    }

    @GetMapping("/ranch/{id}")
    @Operation(summary = "list all dogs by ranch id",description ="list all dogs by ranch id" )
    @ResponseBody
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
    @Operation(summary = "list all dogs by breed's name and ranch id",description = "list all dogs by breed's name and ranch id")
    @ResponseBody
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
