package model.mines;

import model.levels.Hard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MatrixTest {
    private Matrix matrix;

    @BeforeAll
    private void setUp() {
        matrix = new Hard();
    }

    @Test
    void testSetCells_WhenInvokingConstructor_ThenAllIndexesAreCells() {
        Arrays.stream(matrix.getCells()).forEach(array -> Arrays.stream(array).forEach(cell1 -> {
            assertEquals(cell1.getClass(), Cell.class);
        }));
    }

    @Test
    void testSetCells_WhenInvokingConstructor_ThenAllCellsAreUnopened() {
        Arrays.stream(matrix.getCells()).forEach(array -> Arrays.stream(array).forEach(cell -> {
            assertEquals(cell.getCellStatus(), CellStatus.UNOPENED);
        }));
    }
}