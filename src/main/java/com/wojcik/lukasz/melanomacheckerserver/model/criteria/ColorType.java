package com.wojcik.lukasz.melanomacheckerserver.model.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.opencv.core.Scalar;

@Getter
@AllArgsConstructor
public enum ColorType {

    WHITE("White", new Scalar(0, 0, 145), new Scalar(15, 80, 150)),
    RED("Red", new Scalar(0, 60, 100), new Scalar(15, 80, 150)),
    LIGHT_BROWN("Light_Brown", new Scalar(0, 80, 150 + 3), new Scalar(15, 255, 255)),
    DARK_BROWN("Dark_Brown", new Scalar(0, 80, 0), new Scalar(15, 255, 150 - 3)),
    BLUE_GRAY("Blue_Gray", new Scalar(15, 0, 0), new Scalar(179, 255, 150)),
    BLACK("Black", new Scalar(0, 0, 0), new Scalar(15, 140, 90));

    private String name;
    private Scalar lower;
    private Scalar upper;
}
