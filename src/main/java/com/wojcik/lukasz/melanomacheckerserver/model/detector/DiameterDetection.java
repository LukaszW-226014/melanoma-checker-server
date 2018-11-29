package com.wojcik.lukasz.melanomacheckerserver.model.detector;

import com.wojcik.lukasz.melanomacheckerserver.model.criteria.DiameterType;
import com.wojcik.lukasz.melanomacheckerserver.model.criteria.IllegalDiameterSizeException;
import lombok.Data;

@Data
public class DiameterDetection implements DetectionAware<Integer> {

    @Override
    public Integer detect(Integer parameter) {
        if (parameter.equals(DiameterType.EXTRA_SMALL.getValue())) {
            return DiameterType.EXTRA_SMALL.getValue();
        } else if (parameter.equals(DiameterType.SMALL.getValue())) {
            return DiameterType.SMALL.getValue();
        } else if (parameter.equals(DiameterType.MEDIUM.getValue())) {
            return DiameterType.MEDIUM.getValue();
        } else if (parameter.equals(DiameterType.LARGE.getValue())) {
            return DiameterType.LARGE.getValue();
        } else if (parameter.equals(DiameterType.EXTRA_LARGE.getValue())) {
            return DiameterType.EXTRA_LARGE.getValue();
        } else {
            throw new IllegalDiameterSizeException(parameter);
        }
    }
}
