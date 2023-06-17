package Mines;
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

    public void setupMatrix(Matrix matrix) {
        setBombs(matrix);
        setDigits(matrix);
    }

    private void setBombs(Matrix matrix) {
        Random random = new Random();
        int bombCount = 0;

        while (bombCount != matrix.bombCount) {
            int row = random.nextInt(matrix.getCells().length);
            int col = random.nextInt(matrix.getCells()[row].length);

            if (!matrix.getCells()[row][col].isBomb()) {
                matrix.getCells()[row][col].setBomb();
                bombCount++;
            }
        }
    }

    private void setDigits(Matrix matrix) {
        for (int row = 0; row < matrix.getCells().length; row++) {
            for (int col = 0; col < matrix.getCells()[row].length; col++) {
                matrix.getCells()[row][col].setDigit(setDigit(row, col, matrix));
            }
        }
    }

    private int setDigit(int row, int col, Matrix matrix) {
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