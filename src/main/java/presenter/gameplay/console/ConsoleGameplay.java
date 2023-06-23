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

import java.util.function.Predicate;

public class ConsoleGameplay implements Gameplay {
    private final ConsoleView view;
    private final Initializer init;
    private final NeighborOpener opener;

    private Matrix matrix;
    private boolean activeGame;
    private int clickCount;

    ConsoleGameplay(ConsoleView view, Initializer init, NeighborOpener opener) {
        this.view = view;
        this.init = init;
        this.opener = opener;
        clickCount = 0;
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
    public Matrix levelChoice() {
        view.show("""
                Choose level:\s
                1. Easy - 9x9, 23 bombs\s
                2. Medium - 16x16, 60 bombs\s
                3. Hard - 16x30, 115 bombs\s
                4. Expert - 23x34, 200 bombs\s
                Your choice:\s""");

        switch (view.userInput()) {
            case "1":
                return setupMatrix(new Easy());
            case "2":
                return setupMatrix(new Medium());
            case "3":
                return setupMatrix(new Hard());
            case "4":
                return setupMatrix(new Expert());
            default: {
                view.invalidInput();
                return levelChoice();
            }
        }
    }

    @Override
    public void openCell() {
        int[] lineAndCol = view.getLineAndCol();
        clickCount++;
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
        int[] lineAndCol = view.getLineAndCol();
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
        int[] lineAndCol = view.getLineAndCol();
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
            view.showFront(true);
            view.show("""
                    BOOM!\s
                    Game over.\s
                    """);
            reset();
        }
    }

    @Override
    public void reset() {
        clickCount = 0;
        view.show("\n Game reset! \n");
        play();
    }

    private void initOnFirstClick(Cell firstOpened) {
        if (clickCount == 1) {
            init.setMatrix(matrix, firstOpened);
        }
    }

    private void play() {
        levelChoice();
        view.showFront(false);
        activeGame = true;

        while (activeGame) {
            optionChoice();
        }
    }

    private Matrix setupMatrix(Matrix matrix1) {
        matrix = matrix1;
        view.setMatrix(matrix);
        opener.setMatrix(matrix);
        return matrix;
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
                view.showFront(false);
            }
            case "2" -> {
                putFlag();
                view.showFront(false);
            }
            case "3" -> {
                removeFlag();
                view.showFront(false);
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
        int count = 0;
        for (int line = 0; line < matrix.getCells().length; line++) {
            for (int col = 0; col < matrix.getCells()[line].length; col++) {
                Cell cell = matrix.getCells()[line][col];
                if (condition.test(cell)) {
                    count++;
                }
            }
        }
        return count;
    }
}