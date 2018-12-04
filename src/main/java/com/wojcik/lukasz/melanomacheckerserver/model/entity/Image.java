package com.wojcik.lukasz.melanomacheckerserver.model.entity;

import lombok.Data;

@Data
public class Image {

    private String image;
    private Integer diameter;
    private Boolean evolution;
}
