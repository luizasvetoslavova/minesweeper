package presenter.gameplay.gui;

import model.levels.*;
import model.mines.Cell;
import model.mines.CellStatus;
import model.mines.Initializer;
import model.mines.Matrix;
import presenter.gameplay.*;
import view.gui.pages.CustomSizeGetter;
import view.gui.GUIView;
import view.gui.pages.BasePage;
import view.gui.pages.HomePage;
import view.gui.pages.TablePage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;

public class GUIGameplay implements Gameplay {
    private final HomePage homePage;
    private final CellOpener cellOpener;
    private final GUIView view;
    private final ButtonManager buttonManager;
    private final ScoreSaver scoreSaver;

    private TablePage currentTablePage;
    private Matrix currentMatrix;
    private GameTimer gameTimer;
    private CustomSizeGetter customSizeGetter;

    private int openedCount;
    private int clickCount;

    public GUIGameplay() {
        BasePage basePage = new BasePage();
        homePage = basePage.getHomePage();
        openedCount = 0;
        clickCount = 0;
        cellOpener = new CellOpener();
        view = new GUIView();
        buttonManager = new ButtonManager();
        scoreSaver = new ScoreSaver(this);
    }

    @Override
    public void showRules() {
        homePage.setRules("Welcome to Minesweeper!<br><br>" +
                "Rules:<br>" +
                "1. The number shown on an opened cell is the number of mines (bombs) adjacent to it.<br>" +
                "2. You have to flag all the mines and not open any. If you do, you lose and the game ends.<br>" +
                "3. You have to open all numbers. <br>" +
                "You can start by clicking at any random cell.<br><br>" +
                "Signs:<br>" +
                "&#9638; - Empty cell. There are no bombs near it.<br>" +
                "\uD83D\uDCA3 - Bomb.<br>" +
                "&#11036; - Unopened cell.<br>" +
                "&#9873; - Flag.<br><br>" +
                "Regular click to open a cell. Right click to flag.");
    }


    @Override
    public void start() {
        showRules();
        homePage.initHome();
        levelChoice();
    }

    @Override
    public void levelChoice() {
        buttonManager.setupHomeButton(homePage.getEasyBtn());
        buttonManager.setupHomeButton(homePage.getHardBtn());
        buttonManager.setupHomeButton(homePage.getMediumBtn());
        buttonManager.setupHomeButton(homePage.getExpertBtn());
        buttonManager.setupHomeButton(homePage.getCustomBtn());
    }

    @Override
    public void openCell() {
        currentTablePage.getButtons().forEach(tableButton -> {
            tableButton.getButton().addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Cell cell = tableButton.getCell();
                    if (cell.getCellStatus() == CellStatus.OPENED) return;

                    openedCount++;
                    clickCount++;
                    if (openedCount == 1) Initializer.getInstance().initOnFirstClick(cell, openedCount);
                    cell.setCellStatus(CellStatus.OPENED);
                    lose(cell);
                    cellOpener.openNeighbors(cell);
                    view.showAllOpened();
                    win();
                }
            });
        });
    }

    @Override
    public void putFlag() {
        currentTablePage.getButtons().forEach(tableButton -> {
            tableButton.getButton().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Cell cell = tableButton.getCell();
                    if (SwingUtilities.isRightMouseButton(e)) {
                        if (!isEven(tableButton.getTimesClicked()) && cell.getCellStatus() != CellStatus.OPENED) {
                            clickCount++;
                            cell.setCellStatus(CellStatus.FLAGGED);
                            view.setButtonImage(tableButton, view.getFLAG_IMAGE());
                            if (openedCount != 0) win();
                        }
                    }
                }
            });
        });
    }

    @Override
    public void removeFlag() {
        currentTablePage.getButtons().forEach(tableButton -> {
            tableButton.getButton().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Cell cell = tableButton.getCell();

                    if (SwingUtilities.isRightMouseButton(e)) {
                        if (isEven(tableButton.getTimesClicked()) && cell.getCellStatus() == CellStatus.FLAGGED) {
                            clickCount++;
                            cell.setCellStatus(CellStatus.UNOPENED);
                            tableButton.getButton().setIcon(null);
                        }
                    }
                }
            });
        });
    }

    @Override
    public void win() {
        if (new WinChecker(currentMatrix).playerWon()) {
            gameTimer.stop();
            scoreSaver.saveScores();
            JOptionPane.showMessageDialog(null, "CONGRATULATIONS! You won.\n" + getScoreInfo());
            buttonManager.showNextLevelButton();
            buttonManager.deactivateButtons();
        }
    }

    @Override
    public void lose(Cell cell) {
        if (cell.isBomb()) {
            gameTimer.stop();
            cellOpener.openAllBombs();
            view.showAllBombs();
            JOptionPane.showMessageDialog(null, "BOOM! You stepped on a mine.\n" + getScoreInfo());
            buttonManager.deactivateButtons();
        }
    }

    private String getScoreInfo() {
        return "Time: " + getTime() + "\n" +
                "Clicks: " + clickCount + "\n" +
                "Best time for level: " + view.timeMessage(Integer.parseInt(scoreSaver.getTimeScore())) + "\n" +
                "Best clicks for level: " + scoreSaver.getClickScore();
    }

    @Override
    public void reset() {
        currentTablePage.getReset().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentMatrix instanceof Easy) buttonManager.handleButtonAction(new Easy());
                else if (currentMatrix instanceof Medium) buttonManager.handleButtonAction(new Medium());
                else if (currentMatrix instanceof Hard) buttonManager.handleButtonAction(new Hard());
                else if (currentMatrix instanceof Expert) buttonManager.handleButtonAction(new Expert());
                else if (currentMatrix instanceof Custom) buttonManager.handleButtonAction(
                        new Custom(customSizeGetter.getLines(), customSizeGetter.getCols()));
            }
        });
    }

    private void setCustomSize() {
        customSizeGetter = new CustomSizeGetter();
        customSizeGetter.draw();
    }

    private void activateGameplayActions() {
        buttonManager.showPreviousLevelButton();
        openCell();
        putFlag();
        removeFlag();
        reset();
    }

    private static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public int getClickCount() {
        return clickCount;
    }

    public String getTime() {
        return view.timeMessage(gameTimer);
    }

    public Matrix getCurrentMatrix() {
        return currentMatrix;
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }

    private class ButtonManager {
        private void handleButtonAction(Matrix matrix) {
            if (currentTablePage != null) currentTablePage.setVisible(false);
            updateFields(matrix, getClassName(matrix));
            currentTablePage.draw();
            startTimer();
            activateGameplayActions();
        }

        private void showNextLevelButton() {
            if (!(currentMatrix instanceof Expert) && !(currentMatrix instanceof Custom)) {
                currentTablePage.getNextLevel().setVisible(true);
                currentTablePage.getNextLevel().addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Matrix matrix = null;
                        if (currentMatrix instanceof Easy) matrix = new Medium();
                        else if (currentMatrix instanceof Medium) matrix = new Hard();
                        else if (currentMatrix instanceof Hard) matrix = new Expert();
                        handleButtonAction(matrix);
                    }
                });
            }
        }

        private void setupHomeButton(JButton button) {
            button.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!button.equals(homePage.getCustomBtn())) homePage.setVisible(false);

                    Matrix matrix = null;
                    if (button.equals(homePage.getEasyBtn())) matrix = new Easy();
                    else if (button.equals(homePage.getMediumBtn())) matrix = new Medium();
                    else if (button.equals(homePage.getHardBtn())) matrix = new Hard();
                    else if (button.equals(homePage.getExpertBtn())) matrix = new Expert();
                    else if (button.equals(homePage.getCustomBtn())) {
                        setCustomSize();
                        if (customSizeGetter.isSizeInvalid()) return;
                        matrix = new Custom(customSizeGetter.getLines(), customSizeGetter.getCols());
                    }
                    handleButtonAction(matrix);
                }
            });
        }

        private void showPreviousLevelButton() {
            if (!(currentMatrix instanceof Easy) && !(currentMatrix instanceof Custom)) {
                currentTablePage.getPreviousLevel().setVisible(true);
                currentTablePage.getPreviousLevel().addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Matrix matrix = null;
                        if (currentMatrix instanceof Medium) matrix = new Easy();
                        else if (currentMatrix instanceof Hard) matrix = new Medium();
                        else if (currentMatrix instanceof Expert) matrix = new Hard();
                        handleButtonAction(matrix);
                    }
                });
            }
        }

        private void deactivateButtons() {
            currentTablePage.getButtons().forEach(tableButton -> {
                tableButton.getButton().removeNotify();
            });
        }

        private String getClassName(Matrix matrix) {
            return matrix.getClass().getSimpleName().toUpperCase(Locale.ROOT);
        }

        private void updateFields(Matrix matrix, String heading) {
            openedCount = 0;
            clickCount = 0;
            currentTablePage = new TablePage(matrix, homePage, heading);
            view.setTablePage(currentTablePage);
            currentMatrix = currentTablePage.getMatrix();
            Initializer.getInstance().setMatrix(currentMatrix);
            cellOpener.setMatrix(matrix);
        }

        private void startTimer() {
            gameTimer = new GameTimer();
            gameTimer.start();
        }
    }
}