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
import java.util.Locale;
import java.util.function.Predicate;

public class GUIGameplay implements Gameplay {
    private final HomePage homePage;
    private final CellOpener cellOpener;

    private final GUIView view;
    private TablePage currentTablePage;
    private Matrix currentMatrix;
    private int openedCount;

    public GUIGameplay(HomePage homePage) {
        this.homePage = homePage;
        openedCount = 0;
        cellOpener = new CellOpener();
        view = new GUIView();
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
        setupHomeButton(homePage.getEasy());
        setupHomeButton(homePage.getHard());
        setupHomeButton(homePage.getMedium());
        setupHomeButton(homePage.getExpert());
    }

    @Override
    public void openCell() {
        currentTablePage.getButtons().forEach(tableButton -> {
            tableButton.getButton().addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tableButton.getCell().getCellStatus().equals(CellStatus.OPENED)) return;

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
                            win();
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
            deactivateButtons();
        }
    }

    @Override
    public void lose(Cell cell) {
        if (cell.isBomb()) {
            cellOpener.openAllBombs();
            view.showAllBombs();
            JOptionPane.showMessageDialog(null, "BOOM! You stepped on a mine.");
            deactivateButtons();
        }
    }

    @Override
    public void reset() {
        currentTablePage.getReset().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTablePage.getFrame().setVisible(false);

                Matrix matrix = null;
                if (currentMatrix instanceof Easy) {
                    matrix = new Easy();
                } else if (currentMatrix instanceof Medium) {
                    matrix = new Medium();
                } else if (currentMatrix instanceof Hard) {
                    matrix = new Hard();
                } else if (currentMatrix instanceof Expert) {
                    matrix = new Expert();
                }

                updateFields(matrix, getClassName(matrix));
                currentTablePage.draw();
                activateGameplayActions();
            }
        });
    }

    private void setupHomeButton(JButton button) {
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Matrix matrix = null;
                if (button.getText().equals(homePage.getEasy().getText())) {
                    matrix = new Easy();
                } else if (button.getText().equals(homePage.getMedium().getText())) {
                    matrix = new Medium();
                } else if (button.getText().equals(homePage.getHard().getText())) {
                    matrix = new Hard();
                } else if (button.getText().equals(homePage.getExpert().getText())) {
                    matrix = new Expert();
                }

                homePage.getFrame().setVisible(false);
                updateFields(matrix, getClassName(matrix));
                currentTablePage.draw();
                activateGameplayActions();
            }
        });
    }

    private void updateFields(Matrix matrix, String heading) {
        openedCount = 0;
        currentTablePage = new TablePage(matrix, homePage, heading);
        view.setTablePage(currentTablePage);
        currentMatrix = currentTablePage.getMatrix();
        Initializer.getInstance().setMatrix(currentMatrix);
        cellOpener.setMatrix(matrix);
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

    private void deactivateButtons() {
        currentTablePage.getButtons().forEach(tableButton -> {
            tableButton.getButton().removeNotify();
        });
    }

    private String getClassName(Matrix matrix) {
        return matrix.getClass().getSimpleName().toUpperCase(Locale.ROOT);
    }
}