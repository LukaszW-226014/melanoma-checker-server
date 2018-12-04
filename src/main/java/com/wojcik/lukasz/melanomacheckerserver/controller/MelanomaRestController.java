package com.wojcik.lukasz.melanomacheckerserver.controller;

import com.wojcik.lukasz.melanomacheckerserver.model.entity.Image;
import com.wojcik.lukasz.melanomacheckerserver.model.entity.Mole;
import com.wojcik.lukasz.melanomacheckerserver.model.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class MelanomaRestController {

    private static final String DATA_WRITE_PATH = "/Users/lukaszwojcik/Development/melanoma-checker-server/src/main" +
            "/resources/static/write/";

    private static final String DATA_READ_PATH = "/Users/lukaszwojcik/Development/melanoma-checker-server/src/main" +
            "/resources/static/read/";

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

    @PostMapping("/mobile/test1")
    List<Mole> newImageTest(@RequestBody Image image) throws IOException {

        if (!image.getImage().isEmpty()) {
            byte[] imageByte = DatatypeConverter.parseBase64Binary(image.getImage());
            String directory = DATA_READ_PATH + "new1.jpg";
            new FileOutputStream(directory).write(imageByte);
        }

        System.out.println("SUCCESS!!! + diameter: " + image.getDiameter() + " evolution: " + image.getEvolution());

        return repository.findAll();
    }

    @PostMapping("/mobile/test2")
    List<Mole> newImageTest2(@RequestBody java.awt.Image image) {
        System.out.println(image);

        return repository.findAll();

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
