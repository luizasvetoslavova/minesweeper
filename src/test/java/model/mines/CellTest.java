package model.mines;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CellTest {

    @Test
    void testSetBomb_WhenInitializerSetsCellBomb_ThenChangeDigitToMinusOneAndSetIsBombTrue() {
        Cell cell = new Cell();
        cell.setBomb();
        assertTrue(cell.isBomb());
        assertEquals(-1, cell.getDigit());
    }
}
