package com.wojcik.lukasz.melanomacheckerserver.controller;

import com.wojcik.lukasz.melanomacheckerserver.model.entity.Image;
import com.wojcik.lukasz.melanomacheckerserver.model.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ImageController {
    private final ImageRepository repository;

    private MelanomaDetector melanomaDetector;

    @Autowired
    public ImageController(ImageRepository repository, MelanomaDetector melanomaDetector) {
        this.repository = repository;
        this.melanomaDetector = melanomaDetector;
    }

    // Aggregate root

    @GetMapping("/images")
    List<Image> all() {
        melanomaDetector.testCV();
        return repository.findAll();
    }

    @PostMapping("/images")
    Image newImage(@RequestBody Image newImage) {
        return repository.save(newImage);
    }

    // Single item

    @GetMapping("/images/{id}")
    Image one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException(id));
    }

    @PutMapping("/images/{id}")
    Image replaceImage(@RequestBody Image newImage, @PathVariable Long id) {

        return repository.findById(id)
                .map(Image -> {
                    Image.setName(newImage.getName());
                    return repository.save(Image);
                })
                .orElseGet(() -> {
                    newImage.setId(id);
                    return repository.save(newImage);
                });
    }

    @DeleteMapping("/images/{id}")
    void deleteImage(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
