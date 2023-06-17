package Mines;

import java.util.Random;

public class Initializer {
    private final Matrix matrix;
    private final int bombCount;

    public Initializer(Matrix matrix, int bombCount) {
        this.matrix = matrix;
        this.bombCount = bombCount;
    }

    public void setBombs() {
        Random random = new Random();
        int bombCount = 0;

        while (bombCount != this.bombCount) {
            int row = random.nextInt(matrix.getCells().length);
            int col = random.nextInt(matrix.getCells()[row].length);

            if (!matrix.getCells()[row][col].isBomb()) {
                matrix.getCells()[row][col].setBomb();
                bombCount++;
            }
        }
    }

    public void setDigits() {
        for (int row = 0; row < matrix.getCells().length; row++) {
            for (int col = 0; col < matrix.getCells()[row].length; col++) {
                matrix.getCells()[row][col].setDigit(setDigit(row, col));
            }
        }
    }

    private int setDigit(int row, int col) {
        int digit = 0;
        if (!matrix.getCells()[row][col].isBomb()) {
            if (row != 0 && col != 0 && matrix.getCells()[row - 1][col - 1].isBomb())
                digit++;
            if (row != 0 && matrix.getCells()[row - 1][col].isBomb())
                digit++;
            if (row != 0 && col != matrix.getCells()[row].length - 1 && matrix.getCells()[row - 1][col + 1].isBomb())
                digit++;
            if (col != 0 && matrix.getCells()[row][col - 1].isBomb())
                digit++;
            if (col != matrix.getCells()[row].length - 1 && matrix.getCells()[row][col + 1].isBomb())
                digit++;
            if (row != matrix.getCells().length - 1 && col != 0 && matrix.getCells()[row + 1][col - 1].isBomb())
                digit++;
            if (row != matrix.getCells().length - 1 && matrix.getCells()[row + 1][col].isBomb())
                digit++;
            if (row != matrix.getCells().length - 1 && col != matrix.getCells()[row].length - 1
                    && matrix.getCells()[row + 1][col + 1].isBomb())
                digit++;
            return digit;
        }
        return matrix.getCells()[row][col].getDigit();
    }
}