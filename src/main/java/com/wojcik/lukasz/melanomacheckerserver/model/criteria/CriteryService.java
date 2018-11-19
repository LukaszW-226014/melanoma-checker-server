package com.wojcik.lukasz.melanomacheckerserver.model.criteria;

import java.io.File;

public interface CriteryService {

    Float detectAsymmetry(File file);

    Float detectBorder(File file);

    Float detectColour(File file);

    Float detectDiameter(File file);

    Float detectEvolution(boolean isEvolve);
}
