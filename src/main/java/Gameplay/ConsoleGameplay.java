package Gameplay;

import Levels.Easy;
import Levels.Expert;
import Levels.Hard;
import Levels.Medium;
import Mines.Cell;
import Mines.CellStatus;
import Mines.Initializer;
import Mines.Matrix;
import View.ConsoleView;

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
                "~ The number shown on an unlocked cell is the number of mines adjacent to it. \n" +
                "~ You have to flag all the mines and not unlock on a single one, or else you lose and the game ends. \n" +
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
                Easy easy = new Easy();
                matrix = easy;
                init.setupMatrix(matrix);
                return easy;
            case "2":
                Medium medium = new Medium();
                matrix = medium;
                init.setupMatrix(matrix);
                return medium;
            case "3":
                Hard hard = new Hard();
                matrix = hard;
                init.setupMatrix(matrix);
                return hard;
            case "4":
                Expert expert = new Expert();
                matrix = expert;
                init.setupMatrix(matrix);
                return expert;
            default: {
                invalidInput();
                return levelChoice();
            }
        }
    }

    @Override
    public void openCell() {
        int[] lineAndCol = getLineAndCol();
        int line = lineAndCol[0];
        int col = lineAndCol[1];
        if (!matrix.getCells()[line][col].getCellStatus().equals(CellStatus.OPENED)) {
            matrix.getCells()[line][col].setCellStatus(CellStatus.OPENED);
            checkBomb(line, col);
        } else {
            invalidInput();
        }
    }

    @Override
    public void putFlag() {
        int[] lineAndCol = getLineAndCol();
        int line = lineAndCol[0];
        int col = lineAndCol[1];
        if (!matrix.getCells()[line][col].getCellStatus().equals(CellStatus.OPENED)) {
            matrix.getCells()[line][col].setCellStatus(CellStatus.FLAGGED);
            potentialWin();
        } else {
            invalidInput();
        }
    }

    @Override
    public void removeFlag() {
        int[] lineAndCol = getLineAndCol();
        int line = lineAndCol[0];
        int col = lineAndCol[1];
        if (matrix.getCells()[line][col].getCellStatus().equals(CellStatus.FLAGGED)) {
            matrix.getCells()[line][col].setCellStatus(CellStatus.UNOPENED);
        } else {
            invalidInput();
        }
    }

    @Override
    public void reset() {
        view.show("\n Game reset! \n");
        startGame();
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
                showFront();
                break;
            case "2":
                putFlag();
                showFront();
                break;
            case "3":
                removeFlag();
                showFront();
                break;
            case "4":
                reset();
                break;
            case "5":
                view.show("Thank you for playing!");
                activeGame = false;
                break;
            default: {
                invalidInput();
                optionChoice();
                break;
            }
        }
    }

    private void startGame() {
        levelChoice();
        showFront();
        activeGame = true;

        while (activeGame) {
            optionChoice();
        }
    }

    private void invalidInput() {
        view.show("Invalid input, please try again. \n");
    }

    private void checkBomb(int line, int col) {
        if (matrix.getCells()[line][col].isBomb()) {
            showFront();
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
        for(int line = 0; line < matrix.getCells().length; line++) {
            for(int col = 0; col < matrix.getCells()[line].length; col++) {
                Cell currentCell = matrix.getCells()[line][col];
                if(currentCell.isBomb() && currentCell.getCellStatus().equals(CellStatus.FLAGGED)) {
                    count++;
                }
            }
        }
        return count;
    }

    private int totalBombs() {
        int count = 0;
        for(int line = 0; line < matrix.getCells().length; line++) {
            for(int col = 0; col < matrix.getCells()[line].length; col++) {
                Cell currentCell = matrix.getCells()[line][col];
                if(currentCell.isBomb()) {
                    count++;
                }
            }
        }
        return count;
    }

    private void showFront() {
        int numRows = matrix.getCells().length;
        int numCols = matrix.getCells()[0].length;
        printColumnHeaders(numCols);
        printRowsWithCellContents(numRows);
    }

    private void printColumnHeaders(int numCols) {
        view.show("\n");
        view.show("   ");
        for (int index = 0; index < numCols; index++) {
            view.show(String.format(" %d", index));
            view.show(" ");
        }
        view.show("\n");
    }

    private void printRowsWithCellContents(int numRows) {
        for (int line = 0; line < numRows; line++) {
            view.show(String.format("%2d ", line));
            showDifferentCellCases(line);
            view.show("\n");
        }
        view.show("\n");
    }

    private void showDifferentCellCases(int line) {
        int numCols = matrix.getCells()[line].length;

        for (int col = 0; col < numCols; col++) {
            Cell currentCell = matrix.getCells()[line][col];
            CellStatus cellStatus = currentCell.getCellStatus();

            if (cellStatus.equals(CellStatus.OPENED)) {
                if (currentCell.getDigit() == 0) {
                    view.show(" ◽");
                } else if (currentCell.isBomb()) {
                    view.show(" ⬛");
                } else {
                    view.show(String.format(" %d", currentCell.getDigit()));
                }
            } else if (cellStatus.equals(CellStatus.UNOPENED)) {
                view.show(" ⬜");
            } else if (cellStatus.equals(CellStatus.FLAGGED)) {
                view.show(" ⛳");
            }
        }
    }

    private int[] getLineAndCol() {
        view.show("Specify cell line and column (for ex. 1 and 7) \n" +
                "Line (horizontal): ");
        int[] parameters = new int[2];
        try {
            int line = Integer.parseInt(view.userInput());
            view.show("Column (vertical): ");
            int col = Integer.parseInt(view.userInput());

            parameters[0] = line;
            parameters[1] = col;
            return parameters;

        } catch (NumberFormatException e) {
            invalidInput();
            getLineAndCol();
        }
        return parameters;
    }

    //TODO
    private void showNearCells(int line, int col) {

    }
}