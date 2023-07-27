package presenter.gameplay;

import model.levels.*;
import model.mines.Initializer;
import model.mines.Matrix;
import presenter.gameplay.gui.GUIGameplay;
import view.gui.pages.TablePage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Locale;

public class ButtonManager {
    private final GUIGameplay gameplay;

    public ButtonManager(GUIGameplay gameplay) {
        this.gameplay = gameplay;
    }

    public void handleButtonAction(Matrix matrix) {
        if (gameplay.getCurrentTablePage() != null) gameplay.getCurrentTablePage().setVisible(false);
        updateFields(matrix, getClassName(matrix));
        gameplay.getCurrentTablePage().draw();
        startTimer();
        gameplay.activateGameplayActions();
    }

    public void showNextLevelButton() {
        if (!(gameplay.getCurrentMatrix() instanceof Expert) && !(gameplay.getCurrentMatrix() instanceof Custom)) {
            gameplay.getCurrentTablePage().getNextLevel().setVisible(true);
            gameplay.getCurrentTablePage().getNextLevel().addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Matrix matrix = null;
                    if (gameplay.getCurrentMatrix() instanceof Easy) matrix = new Medium();
                    else if (gameplay.getCurrentMatrix() instanceof Medium) matrix = new Hard();
                    else if (gameplay.getCurrentMatrix() instanceof Hard) matrix = new Expert();
                    handleButtonAction(matrix);
                }
            });
        }
    }

    public void setupHomeButton(JButton button) {
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!button.equals(gameplay.getHomePage().getCustomBtn())) gameplay.getHomePage().setVisible(false);

                Matrix matrix = null;
                if (button.equals(gameplay.getHomePage().getEasyBtn())) matrix = new Easy();
                else if (button.equals(gameplay.getHomePage().getMediumBtn())) matrix = new Medium();
                else if (button.equals(gameplay.getHomePage().getHardBtn())) matrix = new Hard();
                else if (button.equals(gameplay.getHomePage().getExpertBtn())) matrix = new Expert();
                else if (button.equals(gameplay.getHomePage().getCustomBtn())) {
                    gameplay.setCustomSize();
                    if (gameplay.getCustomSizeGetter().isSizeInvalid()) return;
                    matrix = new Custom(gameplay.getCustomSizeGetter().getLines(), gameplay.getCustomSizeGetter().getCols());
                }
                handleButtonAction(matrix);
            }
        });
    }

    public void showPreviousLevelButton() {
        if (!(gameplay.getCurrentMatrix() instanceof Easy) && !(gameplay.getCurrentMatrix() instanceof Custom)) {
            gameplay.getCurrentTablePage().getPreviousLevel().setVisible(true);
            gameplay.getCurrentTablePage().getPreviousLevel().addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Matrix matrix = null;
                    if (gameplay.getCurrentMatrix() instanceof Medium) matrix = new Easy();
                    else if (gameplay.getCurrentMatrix() instanceof Hard) matrix = new Medium();
                    else if (gameplay.getCurrentMatrix() instanceof Expert) matrix = new Hard();
                    handleButtonAction(matrix);
                }
            });
        }
    }

    public void deactivateButtons() {
        gameplay.getCurrentTablePage().getButtons().forEach(tableButton -> {
            tableButton.getButton().removeNotify();
        });
    }

    private String getClassName(Matrix matrix) {
        return matrix.getClass().getSimpleName().toUpperCase(Locale.ROOT);
    }

    private void updateFields(Matrix matrix, String heading) {
        gameplay.setOpenedCount(0);
        gameplay.setClickCount(0);
        gameplay.setCurrentTablePage(new TablePage(matrix, gameplay.getHomePage(), heading));
        gameplay.getView().setTablePage(gameplay.getCurrentTablePage());
        gameplay.setCurrentMatrix(gameplay.getCurrentTablePage().getMatrix());
        Initializer.getInstance().setMatrix(gameplay.getCurrentMatrix());
        gameplay.getCellOpener().setMatrix(matrix);
    }

    private void startTimer() {
        gameplay.setGameTimer(new GameTimer());
        gameplay.getGameTimer().start();
    }
}