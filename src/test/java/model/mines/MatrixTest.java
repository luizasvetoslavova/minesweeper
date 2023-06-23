package model.mines;

import model.levels.Easy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MatrixTest {
    private Matrix matrix;

    @BeforeAll
    private void setUp() {
        matrix = new Easy();
    }

    @Test
    void testSetCells_WhenInvokingConstructor_ThenAllIndexesAreCells() {
        for (int line = 0; line < matrix.getCells().length; line++) {
            for (int col = 0; col < matrix.getCells()[line].length; col++) {
                Assertions.assertEquals(matrix.getCells()[line][col].getClass(), Cell.class);
            }
        }
    }

    @Test
    void testSetCells_WhenInvokingConstructor_ThenAllCellsAreUnopened() {
        Arrays.stream(matrix.getCells()).forEach(array -> Arrays.stream(array).forEach(cell -> {
            Assertions.assertEquals(cell.getCellStatus(), CellStatus.UNOPENED);
        }));
    }
}