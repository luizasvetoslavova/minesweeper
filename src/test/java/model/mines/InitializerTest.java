package model.mines;

import model.levels.Hard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import presenter.gameplay.CellOpener;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InitializerTest {
    Initializer initializer;
    private Matrix matrix;
    private Cell firstClicked;

    @BeforeAll
    private void setUpInitializer() {
        initializer = Initializer.getInstance();
    }

    @BeforeEach
    private void openRandomFirstCell() {
        matrix = new Hard();
        initializer.setMatrix(matrix);

        Random random = new Random();
        int line = random.nextInt(matrix.getCells().length);
        int col = random.nextInt(matrix.getCells()[line].length);
        firstClicked = matrix.getCells()[line][col];
        initializer.initOnFirstClick(firstClicked, 1);
    }

    @Test
    void testInitOnFirstClick_WhenFirstCellIsOpened_ThenCellIsNotBomb() {
        assertFalse(firstClicked.isBomb());
    }

    @Test
    void testInitOnFirstClick_WhenFirstCellIsOpened_ThenBombCountEqualsLevelBombCount() {
        assertEquals(matrix.getBombCount(), Arrays.stream(matrix.getCells())
                .flatMap(Arrays::stream)
                .filter(Cell::isBomb)
                .count());
    }

    @Test
    void testInitOnFirstClick_WhenFirstCellIsOpened_ThenDigitsMatchSurroundingBombsCount() {
        Arrays.stream(matrix.getCells())
                .flatMap(Arrays::stream)
                .filter(cell -> cell.getDigit() > 0)
                .forEach(cell -> assertEquals(bombNeighborsCount(cell), cell.getDigit()));
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