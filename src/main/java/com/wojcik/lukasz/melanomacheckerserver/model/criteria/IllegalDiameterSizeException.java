package com.wojcik.lukasz.melanomacheckerserver.model.criteria;

public class IllegalDiameterSizeException extends RuntimeException {
    public IllegalDiameterSizeException(Integer sizeId) {
        super("Illegal diameter size: " + sizeId);
    }
}
