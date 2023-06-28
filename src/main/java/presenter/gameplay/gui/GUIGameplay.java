package presenter.gameplay.gui;

import model.levels.Easy;
import model.levels.Expert;
import model.levels.Hard;
import model.levels.Medium;
import model.mines.Cell;
import presenter.gameplay.Gameplay;
import view.gui.HomePage;
import view.gui.TablePage;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GUIGameplay implements Gameplay {
    private final HomePage homePage;

    public GUIGameplay(HomePage homePage) {
        this.homePage = homePage;
    }

    @Override
    public void rules() {
    }

    @Override
    public void start() {
        homePage.initHome();
        levelChoice();
    }

    @Override
    public void levelChoice() {
        homePage.getEasy().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideHome();
                new TablePage(new Easy(), homePage).draw();
            }
        });

        homePage.getMedium().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideHome();
                new TablePage(new Medium(), homePage).draw();
            }
        });

        homePage.getHard().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideHome();
                new TablePage(new Hard(), homePage).draw();
            }
        });

        homePage.getExpert().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideHome();
                new TablePage(new Expert(), homePage).draw();
            }
        });
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

    private void hideHome() {
        homePage.getFrame().setVisible(false);
    }
}