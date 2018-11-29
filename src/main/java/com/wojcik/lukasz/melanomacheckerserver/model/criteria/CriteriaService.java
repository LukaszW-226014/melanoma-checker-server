package com.wojcik.lukasz.melanomacheckerserver.model.criteria;

import java.io.File;

public interface CriteriaService {

    Float detectAsymmetry(File file);

    Float detectBorder(File file);

    Float detectColour(File file);

    Float detectDiameter(Integer sizeId);

    Float detectEvolution(Boolean isEvolve);
}
