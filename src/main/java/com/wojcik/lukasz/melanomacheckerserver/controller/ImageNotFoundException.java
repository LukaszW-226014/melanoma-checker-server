package com.wojcik.lukasz.melanomacheckerserver.controller;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(Long id) {
        super("Could not find image " + id);
    }
}
