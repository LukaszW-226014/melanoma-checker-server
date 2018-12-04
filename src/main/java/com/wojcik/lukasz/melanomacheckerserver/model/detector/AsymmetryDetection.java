package com.wojcik.lukasz.melanomacheckerserver.model.detector;

public class AsymmetryDetection implements DetectionAware<String>, OpenCvAware {

    @Override
    public Integer detect(String parameter) {
        initCvLib();

        // Return 0-2
        return null;
    }
}
