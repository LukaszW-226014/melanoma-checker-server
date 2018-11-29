package com.wojcik.lukasz.melanomacheckerserver.model.detector;

public interface DetectionAware<T> {

    Integer detect(T parameter);
}
