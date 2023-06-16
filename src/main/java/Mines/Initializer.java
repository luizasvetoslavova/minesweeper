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

        if (matrix.getCells()[row][col].isBomb()) {
            return matrix.getCells()[row][col].getDigit();
        } else {
            //TODO conditions
        }
        return digit;
    }
}