package Gameplay;

import Levels.Easy;
import Levels.Expert;
import Levels.Hard;
import Levels.Medium;
import Mines.CellStatus;
import Mines.Initializer;
import Mines.Matrix;
import View.ConsoleView;

//TODO update matrix design when there is a change
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
        levelChoice();
        showHiddenCellsOnStart();
        activeGame = true;

        while (activeGame) {
            optionChoice();
        }
    }

    @Override
    public void rules() {
        view.show("\n Welcome to Minesweeper! \n Rules: \n" +
                "~ The number shown on an unlocked cell is the number of mines adjacent to it. \n" +
                "~ You have to flag all the mines and not unlock on a single one, or else you lose and the game ends. \n" +
                "You can start by clicking at any random cell. \n \n");
    }

    @Override
    public Matrix levelChoice() {
        view.show("Choose level: \n" +
                "1. Easy \n" +
                "2. Medium \n" +
                "3. Hard \n" +
                "4. Expert \n" +
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

    private void invalidInput() {
        view.show("Invalid input, please try again. \n");
    }

    //TODO update matrix design when there is a change
    @Override
    public void showHiddenCellsOnStart() {
        view.show("\n");
        int iteration = 0;
        for (int line = 0; line < matrix.getCells().length; line++) {
            iteration++;
            if (iteration == 1) {
                for (int i = 0; i < matrix.getCells()[0].length; i++) {
                    view.show(String.format("%2d ", i));
                }
                view.show("\n");
            }
            view.show(String.format("%2d ", line));
            for (int col = 0; col < matrix.getCells()[line].length; col++) {
                view.show(" " + "⬜");
            }
            view.show("\n");
        }
        view.show("\n");
    }

    @Override
    public void optionChoice() {
        view.show("1. Open cell \n" +
                "2. Put flag \n" +
                "3. Remove flag \n" +
                "4. Reset game \n" +
                "5. Exit \n" +
                "Your choice: ");

        switch (view.userInput()) {
            case "1":
                openCell();
                break;
            case "2":
                putFlag();
                break;
            case "3":
                removeFlag();
                break;
            case "4":
                reset();
                break;
            case "5":
                activeGame = false;
            default: {
                invalidInput();
                optionChoice();
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
            openCell();
        }
    }

    private void checkBomb(int line, int col) {
        if (matrix.getCells()[line][col].isBomb()) {
            showOnClick(line, col, "⬛");
            view.show("BOOM! \n" +
                    "Game over.");
        } else {
            showOnClick(line, col, String.valueOf(matrix.getCells()[line][col].getDigit()));
        }
    }

    //TODO update matrix design when there is a change
    private void showOnClick(int line, int col, String sign) {
        int iteration = 0;
        for (int i = 0; i < matrix.getCells().length; i++) {
            iteration++;
            if (iteration == 1) {
                for (int j = 0; j < matrix.getCells()[0].length; j++) {
                    view.show(String.format("%2d ", j));
                }
                view.show("\n");
            }
            view.show(String.format("%2d ", i));
            for (int k = 0; k < matrix.getCells()[i].length; k++) {
                if (matrix.getCells()[line][col] == matrix.getCells()[i][k]) {
                    view.show(sign);
                } else {
                    view.show(" " + "⬜");
                }
            }
            view.show("\n");
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

    @Override
    public void putFlag() {
        int[] lineAndCol = getLineAndCol();
        int line = lineAndCol[0];
        int col = lineAndCol[1];
        if (!matrix.getCells()[line][col].getCellStatus().equals(CellStatus.OPENED)) {
            matrix.getCells()[line][col].setCellStatus(CellStatus.FLAGGED);
            showOnClick(line, col, "⛳");
        } else {
            invalidInput();
            putFlag();
        }
    }

    @Override
    public void removeFlag() {
        int[] lineAndCol = getLineAndCol();
        int line = lineAndCol[0];
        int col = lineAndCol[1];
        if (matrix.getCells()[line][col].getCellStatus().equals(CellStatus.FLAGGED)) {
            matrix.getCells()[line][col].setCellStatus(CellStatus.UNOPENED);
            showOnClick(line, col, "⬜");
        } else {
            invalidInput();
            removeFlag();
        }
    }

    @Override
    public void reset() {
        start();
    }
}