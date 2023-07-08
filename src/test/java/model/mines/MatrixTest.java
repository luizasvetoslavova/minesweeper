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
    private void setUpMatrix() {
        matrix = new Hard();
    }

    @Test
    void testConstructor_WhenMatrixCreated_ThenAllIndexesAreCells() {
        Arrays.stream(matrix.getCells())
                .flatMap(Arrays::stream)
                .forEach(cell -> assertEquals(cell.getClass(), Cell.class));
    }

    @Test
    void testConstructor_WhenMatrixCreated_ThenAllCellsAreUnopened() {
        Arrays.stream(matrix.getCells())
                .flatMap(Arrays::stream)
                .forEach(cell -> assertEquals(cell.getCellStatus(), CellStatus.UNOPENED));
    }
}