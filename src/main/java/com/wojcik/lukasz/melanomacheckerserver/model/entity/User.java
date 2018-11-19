package com.wojcik.lukasz.melanomacheckerserver.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private String nationality;

    @OneToMany(mappedBy = "user")
    private Set<Mole> moles = new HashSet<>();

    public User(String name, String surname, Integer age, String nationality, Set<Mole> moles) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.nationality = nationality;
        this.moles = moles;
    }
}
