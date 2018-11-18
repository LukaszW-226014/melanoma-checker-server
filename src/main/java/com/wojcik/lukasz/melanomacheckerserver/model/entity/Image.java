package com.wojcik.lukasz.melanomacheckerserver.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Image {

    @Id
    @GeneratedValue
    private  Long id;
    private String name;
    private Long userId;
    private Float resultScore;
    private Float asymmetryPoints;
    private Float borderPoints;
    private Float colourPoints;
    private Float diameterPoints;
    private boolean isEvolve;

    public Image(String name) {
        this.name = name;
    }

    public Image(String name, Long userId, Float resultScore, Float asymmetryPoints, Float borderPoints, Float colourPoints, Float diameterPoints, boolean isEvolve) {
        this.name = name;
        this.userId = userId;
        this.resultScore = resultScore;
        this.asymmetryPoints = asymmetryPoints;
        this.borderPoints = borderPoints;
        this.colourPoints = colourPoints;
        this.diameterPoints = diameterPoints;
        this.isEvolve = isEvolve;
    }
}
