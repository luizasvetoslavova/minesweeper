package Gameplay;

import Mines.Matrix;

public interface Gameplay {
    void rules();

    Matrix levelChoice();

    void optionChoice();

    void openCell();

    void putFlag();

    void removeFlag();

    void reset();
}