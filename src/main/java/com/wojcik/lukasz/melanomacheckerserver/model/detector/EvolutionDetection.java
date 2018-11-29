package com.wojcik.lukasz.melanomacheckerserver.model.detector;

import com.wojcik.lukasz.melanomacheckerserver.model.criteria.EvolutionType;
import lombok.Data;

@Data
public class EvolutionDetection implements DetectionAware<Boolean> {

    @Override
    public Float detect(Boolean parameter) {
        if (parameter) {
            return EvolutionType.EVOLVE.getValue().floatValue();
        } else {
            return EvolutionType.NOT_EVOLVE.getValue().floatValue();
        }
    }
}
