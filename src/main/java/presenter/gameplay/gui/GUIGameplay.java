package presenter.gameplay.gui;

import model.levels.*;
import model.mines.Cell;
import model.mines.CellStatus;
import model.mines.Initializer;
import model.mines.Matrix;
import presenter.gameplay.*;
import view.gui.pages.*;
import view.gui.GUIView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        buttonManager = new ButtonManager(this);
        homePage = new HomePage();
        openedCount = 0;
        clickCount = 0;
        cellOpener = new CellOpener();
        view = new GUIView();
        scoreSaver = new ScoreSaver(this);
        new ScorePage(homePage, view);
    }

    @Override
    public void showRules() {
        homePage.setRules("<br>" +
                "<h1 style=\"font-family: 'Times New Roman', serif; font-size: 25px;\">" +
                "Welcome to Minesweeper!" +
                "</h1>" +
                "<br><br>" +
                "<u style=\"font-family: 'Times New Roman', serif; font-size: 15px;\">" +
                "Rules:" +
                "</u><br>" +
                "<p style=\"font-family: 'Times New Roman', serif; font-size: 15px;\">" +
                "1. The number shown on an opened cell is the number of mines (bombs) adjacent to it. " +
                "<br>If a cell is empty (grey), it means that there are no mines adjacent to it.<br>" +
                "2. You need to flag all the mines and not open any. If you do, you lose and the game ends. <br><br>" +
                "All digit squares need to be opened in order for you to win.<br>" +
                "You can start by clicking at any random cell.<br>" +
                "Regular click to open a cell. Right click to flag." +
                "<br><br></p>" +
                "<u style=\"font-family: 'Times New Roman', serif; font-size: 15px;\">" +
                "Icons:" +
                "</u><br><p style=\"font-family: 'Times New Roman', serif; font-size: 15px;\">" +
                "&#9638; - Empty cell. There are no mines near it.<br>" +
                //"<img src=\"https://github.com/luizasvetoslavova/minesweeper/blob/improve-design/src/main/resources/images/bomb.png\">" +
                "\uD83D\uDCA3 - A mine.<br>" +
                "&#11036; - Unopened cell.<br>" +
                "&#9873; - Flag." +
                "<br><br><br>" +
                "Pick your level:" +
                "<p>");
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
            checkNewScore();
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
            view.showMessage("BOOM! You stepped on a mine.\n");
            buttonManager.deactivateButtons();
        }
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

    public int getClickCount() {
        return clickCount;
    }

    public String getTime() {
        return view.timeMessage(gameTimer);
    }

    private static boolean isEven(int number) {
        return number % 2 == 0;
    }

    private String getScoreInfo() {
        return "Time: " + getTime() + "\n" + "Clicks: " + clickCount + "\n";
    }

    private void checkNewScore() {
        if (scoreSaver.isNewScore()) {
            view.showMessage("NEW SCORE!\n" +
                    getScoreInfo() +
                    "Old time score for level: " + view.timeMessage(scoreSaver.getOldTimeScore()) + "\n" +
                    "Old click score for level: " + scoreSaver.getOldClickScore());
        } else {
            view.showMessage("CONGRATULATIONS! You won.\n" +
                    getScoreInfo() +
                    "Best time score for level: " + view.timeMessage(scoreSaver.getTimeScore()) + "\n" +
                    "Best click score for level: " + scoreSaver.getClickScore());
        }
    }

    public Matrix getCurrentMatrix() {
        return currentMatrix;
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }

    public TablePage getCurrentTablePage() {
        return currentTablePage;
    }

    public void setCurrentTablePage(TablePage currentTablePage) {
        this.currentTablePage = currentTablePage;
    }

    public void activateGameplayActions() {
        buttonManager.showPreviousLevelButton();
        openCell();
        putFlag();
        removeFlag();
        reset();
    }

    public void setCurrentMatrix(Matrix currentMatrix) {
        this.currentMatrix = currentMatrix;
    }

    public HomePage getHomePage() {
        return homePage;
    }

    public void setCustomSize() {
        customSizeGetter = new CustomSizeGetter();
        customSizeGetter.draw();
    }

    public CustomSizeGetter getCustomSizeGetter() {
        return customSizeGetter;
    }

    public void setGameTimer(GameTimer gameTimer) {
        this.gameTimer = gameTimer;
    }

    public void setOpenedCount(int openedCount) {
        this.openedCount = openedCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public GUIView getView() {
        return view;
    }

    public CellOpener getCellOpener() {
        return cellOpener;
    }
}