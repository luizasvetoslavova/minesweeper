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

public class ConsoleGameplay implements Gameplay {
    private final ConsoleView view;
    private final Initializer init;

    private Matrix matrix;
    private boolean activeGame;

    public ConsoleGameplay(ConsoleView view, Initializer init) {
        this.view = view;
        this.init = init;
    }

    public void start() {
        rules();
        startGame();
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
        if (!matrix.getCells()[line][col].getCellStatus().equals(CellStatus.OPENED)) {
            matrix.getCells()[line][col].setCellStatus(CellStatus.OPENED);
            checkBomb(line, col);
            showNearCells(line, col);
        } else {
            view.invalidInput();
        }
    }

    @Override
    public void putFlag() {
        int[] lineAndCol = view.getLineAndCol();
        int line = lineAndCol[0];
        int col = lineAndCol[1];
        if (!matrix.getCells()[line][col].getCellStatus().equals(CellStatus.OPENED)) {
            matrix.getCells()[line][col].setCellStatus(CellStatus.FLAGGED);
            potentialWin();
        } else {
            view.invalidInput();
        }
    }

    @Override
    public void removeFlag() {
        int[] lineAndCol = view.getLineAndCol();
        int line = lineAndCol[0];
        int col = lineAndCol[1];
        if (matrix.getCells()[line][col].getCellStatus().equals(CellStatus.FLAGGED)) {
            matrix.getCells()[line][col].setCellStatus(CellStatus.UNOPENED);
        } else {
            view.invalidInput();
        }
    }

    @Override
    public void reset() {
        view.show("\n Game reset! \n");
        startGame();
    }

    private Matrix setupMatrix(Matrix matrix1) {
        matrix = matrix1;
        init.setupMatrix(matrix);
        this.view.setMatrix(matrix);
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
            case "1":
                openCell();
                view.showFront();
                break;
            case "2":
                putFlag();
                view.showFront();
                break;
            case "3":
                removeFlag();
                view.showFront();
                break;
            case "4":
                reset();
                break;
            case "5":
                view.show("Thank you for playing!");
                activeGame = false;
                break;
            default: {
                view.invalidInput();
                optionChoice();
                break;
            }
        }
    }

    private void startGame() {
        levelChoice();
        view.showFront();
        activeGame = true;

        while (activeGame) {
            optionChoice();
        }
    }

    private void checkBomb(int line, int col) {
        if (matrix.getCells()[line][col].isBomb()) {
            view.showFront();
            view.show("BOOM! \n" +
                    "Game over. \n");
            reset();
        }
    }

    private void potentialWin() {
        boolean userWon = (totalBombs() == flaggedBombs());
        if (userWon) {
            view.show("Congratulations! You won!");
            reset();
        }
    }

    private int flaggedBombs() {
        int count = 0;
        for (int line = 0; line < matrix.getCells().length; line++) {
            for (int col = 0; col < matrix.getCells()[line].length; col++) {
                Cell currentCell = matrix.getCells()[line][col];
                if (currentCell.isBomb() && currentCell.getCellStatus().equals(CellStatus.FLAGGED)) {
                    count++;
                }
            }
        }
        return count;
    }

    private int totalBombs() {
        int count = 0;
        for (int line = 0; line < matrix.getCells().length; line++) {
            for (int col = 0; col < matrix.getCells()[line].length; col++) {
                Cell currentCell = matrix.getCells()[line][col];
                if (currentCell.isBomb()) {
                    count++;
                }
            }
        }
        return count;
    }

    private void showNearCells(int line, int col) {
        //!flagged
        //!isBomb
        //random true/false
        //from +5 to +15 cells opened, all near each other
    }
}