package presenter.gameplay.gui;

import model.mines.Cell;
import presenter.gameplay.Gameplay;
import view.gui.GUIView;

public class GUIGameplay implements Gameplay {
    private final GUIView view;

    public GUIGameplay(GUIView view) {
        this.view = view;
    }

    @Override
    public void rules() {
        view.showRules();
    }

    @Override
    public void start() {
        view.initJFrame();
        rules();
        view.addButtons();
    }

    @Override
    public void levelChoice() {

    }

    @Override
    public void openCell() {

    }

    @Override
    public void putFlag() {

    }

    @Override
    public void removeFlag() {

    }

    @Override
    public void win() {

    }

    @Override
    public void lose(Cell cell) {

    }

    @Override
    public void reset() {

    }
}