package presenter.gameplay.gui;

import view.gui.HomePage;

public class GUIStarter {
    public static void main(String[] args) {
        new GUIGameplay(new HomePage()).start();
    }
}