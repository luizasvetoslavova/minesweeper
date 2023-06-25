package presenter.gameplay.gui;

import view.gui.GUIView;

import javax.swing.*;

public class GUIStarter {
    public static void main(String[] args) {
        new GUIGameplay(new GUIView()).start();
    }
}