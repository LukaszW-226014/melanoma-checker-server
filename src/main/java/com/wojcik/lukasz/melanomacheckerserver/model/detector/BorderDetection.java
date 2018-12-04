package com.wojcik.lukasz.melanomacheckerserver.model.detector;

public class BorderDetection implements DetectionAware<String>, OpenCvAware {

    @Override
    public Integer detect(String parameter) {
        initCvLib();


        // Return 0-8
        return null;
    }
}
