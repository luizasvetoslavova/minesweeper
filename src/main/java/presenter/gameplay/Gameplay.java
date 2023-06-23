package presenter.gameplay;

import model.mines.Cell;
import model.mines.Matrix;

public interface Gameplay {
    void rules();

    void start();

    Matrix levelChoice();

    void openCell();

    void putFlag();

    void removeFlag();

    void win();

    void lose(Cell cell);

    void reset();
}