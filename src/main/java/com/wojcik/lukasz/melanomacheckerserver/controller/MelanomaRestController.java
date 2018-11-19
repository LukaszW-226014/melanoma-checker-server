package com.wojcik.lukasz.melanomacheckerserver.controller;

import com.wojcik.lukasz.melanomacheckerserver.model.entity.Mole;
import com.wojcik.lukasz.melanomacheckerserver.model.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
public class MelanomaRestController {

    private final ImageRepository repository;

    private MelanomaDetector melanomaDetector;

    @Autowired
    public MelanomaRestController(ImageRepository repository, MelanomaDetector melanomaDetector) {
        this.repository = repository;
        this.melanomaDetector = melanomaDetector;
    }

    // Aggregate root

    @GetMapping("/images")
    List<Mole> all() {
        melanomaDetector.checkCancerAndSaveResult(new File(""), true);
        return repository.findAll();
    }

    @GetMapping("/detect/{file}/{isEvolve}")
    Mole detect(@PathVariable String file, @PathVariable boolean isEvolve) {
        return melanomaDetector.checkCancerAndSaveResult(new File(file), isEvolve);
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
