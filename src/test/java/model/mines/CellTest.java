package model.mines;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CellTest {
    private Cell cell;

    @BeforeEach
    private void initCell() {
        cell = new Cell();
    }

    @Test
    void testSetBomb_WhenInitializerSetsCellBomb_ThenChangeDigitToMinusOne() {
        cell.setBomb();
        assertEquals(-1, cell.getDigit());
    }

    @Test
    void testSetBomb_WhenInitializerSetsCellBomb_ThenSetIsBombTrue() {
        cell.setBomb();
        assertTrue(cell.isBomb());
    }

    @Test
    void testConstructor_WhenCreatedNewCell_ThenCellStatusUnopened() {
        assertEquals(cell.getCellStatus(), CellStatus.UNOPENED);
    }
}