package presenter.gameplay;

import model.mines.Matrix;

public interface Gameplay {
    void rules();

    Matrix levelChoice();

    void openCell();

    void putFlag();

    void removeFlag();

    void reset();
}