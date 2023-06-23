package view;

import model.mines.Cell;
import model.mines.CellStatus;
import model.mines.Matrix;

import java.util.Scanner;

public class ConsoleView {
    private final Scanner scanner;
    private Matrix matrix;

    public ConsoleView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void show(String string) {
        System.out.print(string);
    }

    public String userInput() {
        return scanner.nextLine();
    }

    public void invalidInput() {
        show("Invalid input, please try again. \n");
    }

    public void showFront(boolean showBombs) {
        int numRows = matrix.getCells().length;
        int numCols = matrix.getCells()[0].length;
        showColumnHeaders(numCols);
        showRows(numRows, showBombs);
    }

    public int[] getLineAndCol() {
        int[] parameters = new int[2];

        show("Specify cell line and column (for ex. 1 and 7) \n" +
                "Line (horizontal): ");
        try {
            int line = Integer.parseInt(userInput());
            show("Column (vertical): ");
            int col = Integer.parseInt(userInput());

            if (line < 0 || line > matrix.getCells().length - 1
                    || col < 0 || col > matrix.getCells()[line].length - 1) {
                invalidInput();
                return getLineAndCol();
            }

            parameters[0] = line;
            parameters[1] = col;
            return parameters;

        } catch (NumberFormatException e) {
            invalidInput();
            getLineAndCol();
        }
        return parameters;
    }

    private void showColumnHeaders(int numCols) {
        show("\n");
        show("   ");
        for (int index = 0; index < numCols; index++) {
            show(String.format(" %d", index));
            show(" ");
        }
        show("\n");
    }

    private void showRows(int numRows, boolean showBombs) {
        for (int line = 0; line < numRows; line++) {
            show(String.format("%2d ", line));
            showCellCases(line, showBombs);
            show("\n");
        }
        show("\n");
    }

    private void showCellCases(int line, boolean showBombs) {
        int numCols = matrix.getCells()[line].length;

        for (int col = 0; col < numCols; col++) {
            Cell currentCell = matrix.getCells()[line][col];
            CellStatus cellStatus = currentCell.getCellStatus();

            if (showBombs && currentCell.isBomb()) {
                show(" ⬛");
            }
            if (cellStatus.equals(CellStatus.OPENED)) {
                if (currentCell.getDigit() == 0) {
                    show(" ◽");
                } else if (currentCell.getDigit() > 0) {
                    show(String.format(" %d", currentCell.getDigit()));
                } else if (currentCell.isBomb()) {
                    show(" ⬛");
                }
            } else if (cellStatus.equals(CellStatus.UNOPENED)) {
                show(" ⬜");
            } else if (cellStatus.equals(CellStatus.FLAGGED)) {
                if (showBombs && !currentCell.isBomb()) {
                    if (currentCell.getDigit() > 0) {
                        show(String.valueOf(currentCell.getDigit()));
                    } else {
                        show(" ◽");
                    }

                }
                show(" ⛳");
            }
        }
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }
}