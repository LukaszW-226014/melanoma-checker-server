package com.wojcik.lukasz.melanomacheckerserver.model.detector;

public interface DetectionAware<T> {

    Float detect(T parameter);
}
