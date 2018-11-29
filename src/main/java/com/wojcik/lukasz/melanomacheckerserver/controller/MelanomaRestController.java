package com.wojcik.lukasz.melanomacheckerserver.controller;

import com.wojcik.lukasz.melanomacheckerserver.model.entity.Mole;
import com.wojcik.lukasz.melanomacheckerserver.model.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class MelanomaRestController {

    private final ImageRepository repository;

    private MelanomaDetectorController melanomaDetectorController;

    @Autowired
    public MelanomaRestController(ImageRepository repository, MelanomaDetectorController melanomaDetectorController) {
        this.repository = repository;
        this.melanomaDetectorController = melanomaDetectorController;
    }

    // Aggregate root

    @GetMapping("/images")
    List<Mole> all() {
        melanomaDetectorController.checkCancerAndSaveResult(new File(""), 1, true);
        return repository.findAll();
    }

    @GetMapping("/detect/{file}/{isEvolve}")
    Mole detect(@PathVariable String file, @PathVariable boolean isEvolve) {
        return melanomaDetectorController.checkCancerAndSaveResult(new File(file), 1, isEvolve);
    }

    @GetMapping("/test/{fileName}")
    List<Mole> testKurwa(@PathVariable String fileName) {
        try {
            melanomaDetectorController.testCase(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return repository.findAll();
    }

    @PostMapping("/images")
    Mole newImage(@RequestBody Mole newMole) {
        return repository.save(newMole);
    }

    // Single item

    @GetMapping("/images/{id}")
    Mole one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException(id));
    }

    @PutMapping("/images/{id}")
    Mole replaceImage(@RequestBody Mole newMole, @PathVariable Long id) {

        return repository.findById(id)
                .map(Mole -> {
                    Mole.setName(newMole.getName());
                    return repository.save(Mole);
                })
                .orElseGet(() -> {
                    newMole.setId(id);
                    return repository.save(newMole);
                });
    }

    @DeleteMapping("/images/{id}")
    void deleteImage(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
