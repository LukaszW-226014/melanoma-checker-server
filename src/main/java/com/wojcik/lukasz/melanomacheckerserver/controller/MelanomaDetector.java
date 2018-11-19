package com.wojcik.lukasz.melanomacheckerserver.controller;

import com.wojcik.lukasz.melanomacheckerserver.model.entity.Mole;
import com.wojcik.lukasz.melanomacheckerserver.model.repository.ImageRepository;
import com.wojcik.lukasz.melanomacheckerserver.service.CriteryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;

@Component
public class MelanomaDetector {

    private static final Float A_CRITERION_MULTIPLEXER = 1.3F;
    private static final Float B_CRITERION_MULTIPLEXER = 0.1F;
    private static final Float C_CRITERION_MULTIPLEXER = 0.5F;
    private static final Float D_CRITERION_MULTIPLEXER = 0.5F;
    private static final Float E_CRITERION_MULTIPLEXER = 0.5F;

    private final ImageRepository repository;

    private CriteryServiceImpl criteryServiceImpl;

    @Autowired
    public MelanomaDetector(ImageRepository repository, CriteryServiceImpl criteryServiceImpl) {
        this.repository = repository;
        this.criteryServiceImpl = criteryServiceImpl;
    }

    public Mole checkCancerAndSaveResult(File file, boolean isEvolve) {
        Float asymmetryPoints = criteryServiceImpl.detectAsymmetry(file);
        Float borderPoints = criteryServiceImpl.detectBorder(file);
        Float colourPoints = criteryServiceImpl.detectColour(file);
        Float diameterPoints = criteryServiceImpl.detectDiameter(file);
        Float evolutionPoints = criteryServiceImpl.detectEvolution(isEvolve);

        Float resultScore = A_CRITERION_MULTIPLEXER * asymmetryPoints
                + B_CRITERION_MULTIPLEXER * borderPoints
                + C_CRITERION_MULTIPLEXER * colourPoints
                + D_CRITERION_MULTIPLEXER * diameterPoints
                + E_CRITERION_MULTIPLEXER * evolutionPoints;

        return repository.save(new Mole(file.getName(), null, LocalDate.now(), null, asymmetryPoints, borderPoints, colourPoints, diameterPoints, evolutionPoints, resultScore));
    }
}
