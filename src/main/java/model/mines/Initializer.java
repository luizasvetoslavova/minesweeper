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

    public void setupMatrix(Matrix matrix) {
        setBombs(matrix);
        setDigits(matrix);
    }

    private void setBombs(Matrix matrix) {
        Random random = new Random();
        int bombCount = 0;

        while (bombCount != matrix.bombCount) {
            int line = random.nextInt(matrix.getCells().length);
            int col = random.nextInt(matrix.getCells()[line].length);

            if (!matrix.getCells()[line][col].isBomb()) {
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
            if (line != 0 && col != 0 && matrix.getCells()[line - 1][col - 1].isBomb())
                digit++;
            if (line != 0 && matrix.getCells()[line - 1][col].isBomb())
                digit++;
            if (line != 0 && col != matrix.getCells()[line].length - 1 && matrix.getCells()[line - 1][col + 1].isBomb())
                digit++;
            if (col != 0 && matrix.getCells()[line][col - 1].isBomb())
                digit++;
            if (col != matrix.getCells()[line].length - 1 && matrix.getCells()[line][col + 1].isBomb())
                digit++;
            if (line != matrix.getCells().length - 1 && col != 0 && matrix.getCells()[line + 1][col - 1].isBomb())
                digit++;
            if (line != matrix.getCells().length - 1 && matrix.getCells()[line + 1][col].isBomb())
                digit++;
            if (line != matrix.getCells().length - 1 && col != matrix.getCells()[line].length - 1
                    && matrix.getCells()[line + 1][col + 1].isBomb())
                digit++;
            return digit;
        }
        return matrix.getCells()[line][col].getDigit();
    }
}