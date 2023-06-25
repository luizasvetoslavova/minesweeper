package presenter.gameplay.console;

import model.levels.Easy;
import model.levels.Expert;
import model.levels.Hard;
import model.levels.Medium;
import model.mines.Cell;
import model.mines.CellStatus;
import model.mines.Initializer;
import model.mines.Matrix;
import presenter.gameplay.Gameplay;
import presenter.gameplay.NeighborOpener;
import view.ConsoleView;

import java.util.Arrays;
import java.util.function.Predicate;

public class ConsoleGameplay implements Gameplay {
    private ConsoleView view;
    private final Initializer init;
    private NeighborOpener opener;

    private Matrix matrix;
    private boolean activeGame;
    private int openedCount;

    public ConsoleGameplay(ConsoleView view, Initializer init, NeighborOpener opener) {
        this.view = view;
        this.init = init;
        this.opener = opener;
        openedCount = 0;
    }

    @Override
    public void rules() {
        view.show("""

                 Welcome to Minesweeper!\s
                 Rules:\s
                ~ The number shown on an unlocked cell is the number of model.mines adjacent to it.\s
                ~ You have to flag all the model.mines and not unlock on a single one, or else you lose and the game ends.\s
                You can start by clicking at any random cell.\s
                \s
                Signs:\s
                ◽ - Empty cell. There are no bombs near it.\s
                ⬛ - Bomb.\s
                ⬜ - Unopened cell.\s
                ⛳ - Flag.\s
                \s
                """);
    }

    @Override
    public void start() {
        rules();
        play();
    }

    @Override
    public void levelChoice() {
        view.show("""
                Choose level:\s
                1. Easy - 9x9, 23 bombs\s
                2. Medium - 16x16, 60 bombs\s
                3. Hard - 16x30, 115 bombs\s
                4. Expert - 23x34, 200 bombs\s
                Your choice:\s""");

        switch (view.userInput()) {
            case "1" -> setupMatrix(new Easy());
            case "2" -> setupMatrix(new Medium());
            case "3" -> setupMatrix(new Hard());
            case "4" -> setupMatrix(new Expert());
            default  ->  {
                view.invalidInput();
                levelChoice();
            }
        }
    }

    @Override
    public void openCell() {
        int[] lineAndCol = getLineAndCol();
        openedCount++;
        int line = lineAndCol[0];
        int col = lineAndCol[1];
        Cell cell = matrix.getCells()[line][col];

        if (!cell.getCellStatus().equals(CellStatus.OPENED)) {
            cell.setCellStatus(CellStatus.OPENED);
            initOnFirstClick(cell);
            lose(cell);
            opener.openNeighbors(cell);
            win();
        } else {
            view.invalidInput();
        }
    }

    @Override
    public void putFlag() {
        int[] lineAndCol = getLineAndCol();
        int line = lineAndCol[0];
        int col = lineAndCol[1];
        Cell cell = matrix.getCells()[line][col];
        if (!cell.getCellStatus().equals(CellStatus.OPENED)) {
            cell.setCellStatus(CellStatus.FLAGGED);
            win();
        } else {
            view.invalidInput();
        }
    }

    @Override
    public void removeFlag() {
        int[] lineAndCol = getLineAndCol();
        int line = lineAndCol[0];
        int col = lineAndCol[1];
        Cell cell = matrix.getCells()[line][col];
        if (cell.getCellStatus().equals(CellStatus.FLAGGED)) {
            cell.setCellStatus(CellStatus.UNOPENED);
        } else {
            view.invalidInput();
        }
    }

    @Override
    public void win() {
        int allDigits = countCells(cell -> cell.getDigit() > 0);
        int openedDigits = countCells(cell -> cell.getDigit() > 0 && cell.getCellStatus().equals(CellStatus.OPENED));
        int flaggedBombs = countCells(cell -> cell.isBomb() && cell.getCellStatus().equals(CellStatus.FLAGGED));
        int totalBombs = countCells(Cell::isBomb);

        boolean userWon = totalBombs == flaggedBombs && allDigits == openedDigits;
        if (userWon) {
            view.show("Congratulations! You won!");
            reset();
        }
    }

    @Override
    public void lose(Cell cell) {
        if (cell.isBomb()) {
            openAllBombs();
            view.showFront();
            view.show("""
                    BOOM!\s
                    Game over.\s
                    """);
            reset();
        }
    }

    @Override
    public void reset() {
        openedCount = 0;
        view.show("\n Game reset! \n");
        play();
    }

    private int[] getLineAndCol() {
        int[] lineAndCol = view.getLineAndCol();
        while (lineAndCol == null) {
            lineAndCol = view.getLineAndCol();
        }
        return lineAndCol;
    }

    private void openAllBombs() {
        Arrays.stream(matrix.getCells()).forEach(array -> Arrays.stream(array).forEach(cell -> {
            if (cell.isBomb()) cell.setCellStatus(CellStatus.OPENED);
        }));
    }

    private void initOnFirstClick(Cell firstOpened) {
        if (openedCount == 1) {
            init.setMatrix(matrix, firstOpened);
        }
    }

    private void play() {
        levelChoice();
        view.showFront();
        activeGame = true;

        while (activeGame) {
            optionChoice();
        }
    }

    private void setupMatrix(Matrix matrix1) {
        matrix = matrix1;
        view.setMatrix(matrix);
        opener.setMatrix(matrix);
    }

    private void optionChoice() {
        view.show("""
                1. Open cell\s
                2. Put flag\s
                3. Remove flag\s
                4. Reset game\s
                5. Exit\s
                Your choice:\s""");

        switch (view.userInput()) {
            case "1" -> {
                openCell();
                view.showFront();
            }
            case "2" -> {
                putFlag();
                view.showFront();
            }
            case "3" -> {
                removeFlag();
                view.showFront();
            }
            case "4" -> reset();
            case "5" -> {
                view.show("Thank you for playing!");
                activeGame = false;
            }
            default -> {
                view.invalidInput();
                optionChoice();
            }
        }
    }

    private int countCells(Predicate<Cell> condition) {
        final int[] count = {0};
        Arrays.stream(matrix.getCells()).forEach(array -> Arrays.stream(array).forEach(cell -> {
            if (condition.test(cell)) count[0]++;
        }));
        return count[0];
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }
}