package presenter.gameplay.gui;

import model.levels.Easy;
import model.levels.Expert;
import model.levels.Hard;
import model.levels.Medium;
import model.mines.Cell;
import model.mines.CellStatus;
import model.mines.Initializer;
import model.mines.Matrix;
import presenter.gameplay.CellOpener;
import presenter.gameplay.Gameplay;
import view.gui.GUIView;
import view.gui.HomePage;
import view.gui.TablePage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.function.Predicate;

public class GUIGameplay implements Gameplay {
    private final HomePage homePage;
    private final CellOpener cellOpener;

    private GUIView view;
    private TablePage currentTablePage;
    private Matrix currentMatrix;
    private int openedCount;

    public GUIGameplay(HomePage homePage) {
        this.homePage = homePage;
        openedCount = 0;
        cellOpener = new CellOpener();
    }

    @Override
    public void rules() {
        homePage.setRules("Welcome to Minesweeper!" + "<br>" + "<br>" + "Rules:" + "<br>" +
                "1. The number shown on an opened cell is the number of mines (bombs) adjacent to it." + "<br>" +
                "2. You have to flag all the mines and not unlock on a single one, or else you lose and " +
                "the game ends." + "<br>" + "You can start by clicking at any random cell." + "<br>" + "<br>" +
                "Signs:" + "<br>" + "◽ - Empty cell. There are no bombs near it." + "<br>" + "⬛ - Bomb." + "<br>" +
                "⬜ - Unopened cell." + "<br>" + "⛳ - Flag." + "<br>" + "<br>" + "Left click to open a cell. " +
                "Right click to flag.");
    }

    @Override
    public void start() {
        rules();
        homePage.initHome();
        levelChoice();
    }

    @Override
    public void levelChoice() {
        setupHomeButton(homePage.getEasy(), new Easy(), "EASY");
        setupHomeButton(homePage.getMedium(), new Medium(), "MEDIUM");
        setupHomeButton(homePage.getHard(), new Hard(), "HARD");
        setupHomeButton(homePage.getExpert(), new Expert(), "EXPERT");
    }

    @Override
    public void openCell() {
        currentTablePage.getButtons().forEach(tableButton -> {
            tableButton.getButton().addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openedCount++;
                    if (openedCount == 1) {
                        Initializer.getInstance().initOnFirstClick(tableButton.getCell(), openedCount);
                    }
                    tableButton.getCell().setCellStatus(CellStatus.OPENED);

                    lose(tableButton.getCell());
                    cellOpener.openNeighbors(tableButton.getCell());
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
                    if (SwingUtilities.isRightMouseButton(e)) {
                        if (!isEven(tableButton.getTimesClicked()) &&
                                !tableButton.getCell().getCellStatus().equals(CellStatus.OPENED)) {
                            tableButton.getCell().setCellStatus(CellStatus.FLAGGED);
                            view.setButtonImage(tableButton, view.getFLAG_IMAGE());
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
                    if (SwingUtilities.isRightMouseButton(e)) {
                        if (isEven(tableButton.getTimesClicked())
                                && tableButton.getCell().getCellStatus().equals(CellStatus.FLAGGED)) {
                            tableButton.getCell().setCellStatus(CellStatus.UNOPENED);
                            tableButton.getButton().setIcon(null);
                        }
                    }
                }
            });
        });
    }

    @Override
    public void win() {
        int allDigits = countCells(cell -> cell.getDigit() > 0);
        int openedDigits = countCells(cell -> cell.getDigit() > 0 && cell.getCellStatus().equals(CellStatus.OPENED));
        int flaggedBombs = countCells(cell -> cell.isBomb() && cell.getCellStatus().equals(CellStatus.FLAGGED));
        int totalBombs = countCells(Cell::isBomb);

        boolean userWon = (totalBombs == flaggedBombs) && (allDigits == openedDigits);
        if (userWon) {
            JOptionPane.showMessageDialog(null, "CONGRATULATIONS! You won.");
        }
    }

    @Override
    public void lose(Cell cell) {
        if (cell.isBomb()) {
            cellOpener.openAllBombs();
            view.showAllBombs();
            JOptionPane.showMessageDialog(null, "BOOM! You stepped on a mine.");
            currentTablePage.getButtons().forEach(tableButton -> {
                tableButton.getButton().removeNotify();
            });
        }
    }

    @Override
    public void reset() {
//        currentTablePage.getReset().addActionListener(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                currentTablePage.getFrame().setVisible(false);
//                openedCount = 0;
//                //todo
//            }
//        });
    }

    private void setupHomeButton(JButton button, Matrix matrix, String heading) {
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homePage.getFrame().setVisible(false);
                initDependencies(matrix, heading);
                currentTablePage.draw();
                activateGameplayActions();
            }
        });
    }

    private void initDependencies(Matrix matrix, String heading) {
        openedCount = 0;
        currentTablePage = new TablePage(matrix, homePage, heading);
        currentMatrix = currentTablePage.getMatrix();
        view = new GUIView(currentTablePage);
        Initializer.getInstance().setMatrix(currentMatrix);
        cellOpener.setMatrix(matrix);
        openedCount = 0;
    }

    private void activateGameplayActions() {
        openCell();
        putFlag();
        removeFlag();
        reset();
    }

    private static boolean isEven(int number) {
        return number % 2 == 0;
    }

    private int countCells(Predicate<Cell> condition) {
        final int[] count = {0};
        Arrays.stream(currentMatrix.getCells()).flatMap(Arrays::stream).forEach(cell -> {
            if (condition.test(cell)) count[0]++;
        });
        return count[0];
    }
}