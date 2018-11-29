package com.wojcik.lukasz.melanomacheckerserver.model.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EvolutionType {

    EVOLVE(1),
    NOT_EVOLVE(0);

    private Integer value;
}
