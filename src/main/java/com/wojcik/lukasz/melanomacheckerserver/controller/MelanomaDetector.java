package com.wojcik.lukasz.melanomacheckerserver.controller;

import com.wojcik.lukasz.melanomacheckerserver.model.criteria.*;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class MelanomaDetector implements AsymmetryCriterion, BorderCriterion, ColourCriterion, DiameterCriterion, EvolutionCriterion {



    @Override
    public Float detectAsymmetry() {
        return null;
    }

    public Float checkCarcinogenicity() {
        detectAsymmetry();
        return null;
    }

    public void testCV() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
        System.out.println("mat = " + mat.dump());

        File file = new File()
    }
}
