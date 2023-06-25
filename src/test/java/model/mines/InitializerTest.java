package model.mines;

import model.levels.Hard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import presenter.gameplay.CellOpener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InitializerTest {
    private Matrix matrix;
    private Cell firstClicked;

    @BeforeAll
    private void setUpMatrixAndFirstClicked() {
        Initializer initializer = Initializer.getInstance();
        matrix = new Hard();

        Random random = new Random();
        int line = random.nextInt(matrix.getCells().length);
        int col = random.nextInt(matrix.getCells()[line].length);
        firstClicked = matrix.getCells()[line][col];
        initializer.initOnFirstClick(firstClicked, 1, matrix);
    }

    @Test
    void testSetMatrix_WhenFirstCellIsOpened_ThenCellIsNotBomb() {
        assertFalse(firstClicked.isBomb());
    }

    @Test
    void testSetMatrix_WhenInitialized_ThenBombCountEqualsLevelBombCount() {
        final int[] bombCount = {0};
        Arrays.stream(matrix.getCells()).forEach(array -> Arrays.stream(array).forEach(cell -> {
            if (cell.isBomb()) bombCount[0]++;
        }));
        assertEquals(matrix.getBombCount(), bombCount[0]);
    }

    @Test
    void testSetMatrix_WhenInitialized_ThenDigitsMatchSurroundingBombsCount() {
        List<Cell> digits = new ArrayList<>();
        Arrays.stream(matrix.getCells()).forEach(array -> Arrays.stream(array).forEach(cell -> {
            if (cell.getDigit() > 0) digits.add(cell);
        }));

        digits.forEach(cell -> assertEquals(bombNeighborsCount(cell), cell.getDigit()));
    }

    private int bombNeighborsCount(Cell cell) {
        CellOpener opener = new CellOpener();
        opener.setMatrix(matrix);
        int counter = 0;

        for (int line = 0; line < matrix.getCells().length; line++) {
            for (int col = 0; col < matrix.getCells()[line].length; col++) {
                if (opener.isNeighbor(cell, line, col) && matrix.getCells()[line][col].isBomb()) {
                    counter++;
                }
            }
        }
        return counter;
    }
}