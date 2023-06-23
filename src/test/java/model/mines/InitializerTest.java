package model.mines;

import model.levels.Hard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import presenter.gameplay.NeighborOpener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InitializerTest {
    private Matrix matrix;
    private Cell firstClicked;

    @BeforeAll
    private void setUp() {
        Initializer initializer = Initializer.getInstance();
        matrix = new Hard();

        Random random = new Random();
        int line = random.nextInt(matrix.getCells().length);
        int col = random.nextInt(matrix.getCells()[line].length);
        firstClicked = matrix.getCells()[line][col];

        initializer.setMatrix(matrix, firstClicked);
    }

    @Test
    void testSetMatrix_WhenFirstCellIsOpened_ThenCellIsNotBomb() {
        Assertions.assertFalse(firstClicked.isBomb());
    }

    @Test
    void testSetMatrix_WhenInitialized_ThenBombCountEqualsLevelBombCount() {
        final int[] bombCount = {0};
        Arrays.stream(matrix.getCells()).forEach(array -> Arrays.stream(array).forEach(cell -> {
            if (cell.isBomb()) bombCount[0]++;
        }));

        Assertions.assertEquals(matrix.getBombCount(), bombCount[0]);
    }

    @Test
    void testSetMatrix_WhenInitialized_ThenDigitsMatchSurroundingBombsCount() {
        List<Cell> digits = new ArrayList<>();
        Arrays.stream(matrix.getCells()).forEach(array -> Arrays.stream(array).forEach(cell -> {
            if (cell.getDigit() > 0) digits.add(cell);
        }));

        digits.forEach(cell -> Assertions.assertEquals(bombNeighborsCount(cell), cell.getDigit()));
    }

    private int bombNeighborsCount(Cell cell) {
        NeighborOpener opener = new NeighborOpener();
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