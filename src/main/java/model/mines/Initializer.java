package model.mines;

import java.util.Random;

public class Initializer {
    private static Initializer instance = null;

    public static Initializer getInstance() {
        if (instance == null) {
            instance = new Initializer();
        }
        return instance;
    }

    private Initializer() {
    }

    public void setMatrix(Matrix matrix, Cell firstOpened) {
        setBombs(matrix, firstOpened);
        setDigits(matrix);
    }

    private void setBombs(Matrix matrix, Cell firstOpened) {
        Random random = new Random();
        int bombCount = 0;

        while (bombCount < matrix.getBombCount()) {
            int line = random.nextInt(matrix.getCells().length);
            int col = random.nextInt(matrix.getCells()[line].length);

            if (!matrix.getCells()[line][col].isBomb() && !matrix.getCells()[line][col].equals(firstOpened)) {
                matrix.getCells()[line][col].setBomb();
                bombCount++;
            }
        }
    }

    private void setDigits(Matrix matrix) {
        for (int line = 0; line < matrix.getCells().length; line++) {
            for (int col = 0; col < matrix.getCells()[line].length; col++) {
                matrix.getCells()[line][col].setDigit(setDigit(line, col, matrix));
            }
        }
    }

    private int setDigit(int line, int col, Matrix matrix) {
        int digit = 0;

        if (!matrix.getCells()[line][col].isBomb()) {
            int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

            for (int[] direction : directions) {
                int neighborLine = line + direction[0];
                int neighborCol = col + direction[1];

                if (isValidPosition(neighborLine, neighborCol, matrix)
                        && matrix.getCells()[neighborLine][neighborCol].isBomb()) {
                    digit++;
                }
            }
        }
        return digit;
    }

    private boolean isValidPosition(int line, int col, Matrix matrix) {
        return line >= 0 && line < matrix.getCells().length && col >= 0 && col < matrix.getCells()[line].length;
    }
}