package com.wojcik.lukasz.melanomacheckerserver.model.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiameterType {

    EXTRA_SMALL(1),
    SMALL(2),
    MEDIUM(3),
    LARGE(4),
    EXTRA_LARGE(5);

    private Integer value;
}
