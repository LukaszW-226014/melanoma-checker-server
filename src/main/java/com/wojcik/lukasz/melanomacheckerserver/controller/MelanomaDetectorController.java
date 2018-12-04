package com.wojcik.lukasz.melanomacheckerserver.controller;

import com.wojcik.lukasz.melanomacheckerserver.model.entity.Mole;
import com.wojcik.lukasz.melanomacheckerserver.model.repository.ImageRepository;
import com.wojcik.lukasz.melanomacheckerserver.service.CriteriaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Component
public class MelanomaDetectorController {

    private static final Float A_CRITERION_MULTIPLEXER = 1.3F;
    private static final Float B_CRITERION_MULTIPLEXER = 0.1F;
    private static final Float C_CRITERION_MULTIPLEXER = 0.5F;
    private static final Float D_CRITERION_MULTIPLEXER = 0.5F;
    private static final Float E_CRITERION_MULTIPLEXER = 0.5F;

    private final ImageRepository repository;

    private CriteriaServiceImpl criteriaServiceImpl;

    @Autowired
    public MelanomaDetectorController(ImageRepository repository, CriteriaServiceImpl criteriaServiceImpl) {
        this.repository = repository;
        this.criteriaServiceImpl = criteriaServiceImpl;
    }

    public Mole checkCancerAndSaveResult(File file, Integer sizeId, Boolean isEvolve) {
        Float asymmetryPoints = criteriaServiceImpl.detectAsymmetry(file).floatValue();
        Float borderPoints = criteriaServiceImpl.detectBorder(file).floatValue();
        Float colourPoints = criteriaServiceImpl.detectColour(file).floatValue();
        Float diameterPoints = criteriaServiceImpl.detectDiameter(sizeId).floatValue();
        Float evolutionPoints = criteriaServiceImpl.detectEvolution(isEvolve).floatValue();

        Float resultScore = A_CRITERION_MULTIPLEXER * asymmetryPoints
                + B_CRITERION_MULTIPLEXER * borderPoints
                + C_CRITERION_MULTIPLEXER * colourPoints
                + D_CRITERION_MULTIPLEXER * diameterPoints
                + E_CRITERION_MULTIPLEXER * evolutionPoints;

        return repository.save(new Mole(file.getName(), null, LocalDate.now(), null, asymmetryPoints, borderPoints, colourPoints, diameterPoints, evolutionPoints, resultScore));
    }

    public void testCase(String fileName) throws IOException {
        criteriaServiceImpl.testDetectBorderMethod(fileName);
        //criteriaServiceImpl.testDetectColorMethod(fileName);
        //criteriaServiceImpl.testDetectBorderMethod2(fileName);
    }
}
