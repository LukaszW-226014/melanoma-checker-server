package com.wojcik.lukasz.melanomacheckerserver.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Mole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate date;
    private String description;
    private Float asymmetryPoints;
    private Float borderPoints;
    private Float colourPoints;
    private Float diameterPoints;
    private Float evolutionPoints;
    private Float resultScore;

    public Mole(String name) {
        this.name = name;
    }

    public Mole(String name, User user, LocalDate date, String description, Float asymmetryPoints, Float borderPoints, Float colourPoints, Float diameterPoints, Float evolutionPoints, Float resultScore) {
        this.name = name;
        this.user = user;
        this.date = date;
        this.description = description;
        this.asymmetryPoints = asymmetryPoints;
        this.borderPoints = borderPoints;
        this.colourPoints = colourPoints;
        this.diameterPoints = diameterPoints;
        this.evolutionPoints = evolutionPoints;
        this.resultScore = resultScore;
    }
}
