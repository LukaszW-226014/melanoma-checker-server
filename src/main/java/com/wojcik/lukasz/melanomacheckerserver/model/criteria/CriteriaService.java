package com.wojcik.lukasz.melanomacheckerserver.model.criteria;

import java.io.File;

public interface CriteriaService {

    Integer detectAsymmetry(File file);

    Integer detectBorder(File file);

    Integer detectColour(File file);

    Integer detectDiameter(Integer sizeId);

    Integer detectEvolution(Boolean isEvolve);
}
