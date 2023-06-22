package presenter.gameplay;

import model.levels.Easy;
import model.levels.Expert;
import model.levels.Hard;
import model.levels.Medium;
import model.mines.Cell;
import model.mines.CellStatus;
import model.mines.Initializer;
import model.mines.Matrix;
import view.ConsoleView;

import java.util.function.Predicate;

public class ConsoleGameplay implements Gameplay {
    private final ConsoleView view;
    private final Initializer init;
    private final NeighborOpener opener;

    private Matrix matrix;
    private boolean activeGame;

    public ConsoleGameplay(ConsoleView view, Initializer init, NeighborOpener opener) {
        this.view = view;
        this.init = init;
        this.opener = opener;
    }

    @Override
    public void rules() {
        view.show("\n Welcome to Minesweeper! \n Rules: \n" +
                "~ The number shown on an unlocked cell is the number of model.mines adjacent to it. \n" +
                "~ You have to flag all the model.mines and not unlock on a single one, or else you lose and the game ends. \n" +
                "You can start by clicking at any random cell. \n \n" +
                "Signs: \n" +
                "◽ - Empty cell. There are no bombs near it. \n" +
                "⬛ - Bomb. \n" +
                "⬜ - Unopened cell. \n" +
                "⛳ - Flag. \n \n");
    }

    @Override
    public void start() {
        rules();
        play();
    }

    @Override
    public Matrix levelChoice() {
        view.show("Choose level: \n" +
                "1. Easy - 9x9, 23 bombs \n" +
                "2. Medium - 16x16, 60 bombs \n" +
                "3. Hard - 16x30, 115 bombs \n" +
                "4. Expert - 23x34, 200 bombs \n" +
                "Your choice: ");

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
        int line = lineAndCol[0];
        int col = lineAndCol[1];
        Cell cell = matrix.getCells()[line][col];
        if (!cell.getCellStatus().equals(CellStatus.OPENED)) {
            cell.setCellStatus(CellStatus.OPENED);
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
            view.showFrontOnLose();
            view.show("BOOM! \n" +
                    "Game over. \n");
            reset();
        }
    }

    @Override
    public void reset() {
        view.show("\n Game reset! \n");
        play();
    }

    private void play() {
        levelChoice();
        view.showFront();
        activeGame = true;

        while (activeGame) {
            optionChoice();
        }
    }

    private Matrix setupMatrix(Matrix matrix1) {
        matrix = matrix1;
        init.setMatrix(matrix);
        view.setMatrix(matrix);
        opener.setMatrix(matrix);
        return matrix;
    }

    private void optionChoice() {
        view.show("1. Open cell \n" +
                "2. Put flag \n" +
                "3. Remove flag \n" +
                "4. Reset game \n" +
                "5. Exit \n" +
                "Your choice: ");

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