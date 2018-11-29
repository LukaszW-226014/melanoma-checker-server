package com.wojcik.lukasz.melanomacheckerserver.model.detector;

public class OutOfRangeDetectorValueException extends RuntimeException {
    public OutOfRangeDetectorValueException(Integer score, String detectorClassName) {
        super("Detector: " + detectorClassName + " | Score: " + score + " out of range!");
    }
}
