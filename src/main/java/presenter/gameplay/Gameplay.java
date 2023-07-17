package presenter.gameplay;

import model.mines.Cell;

public interface Gameplay {
    void showRules();

    void start();

    void levelChoice();

    void openCell();

    void putFlag();

    void removeFlag();

    void win();

    void lose(Cell cell);

    void reset();
}