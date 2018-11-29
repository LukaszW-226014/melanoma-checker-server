package com.wojcik.lukasz.melanomacheckerserver.model.detector;

import org.opencv.core.Core;

public interface OpenCvAware {

    default void initCvLib() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
}
