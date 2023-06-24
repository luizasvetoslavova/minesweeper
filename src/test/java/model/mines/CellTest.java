package model.mines;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CellTest {

    @Test
    void testSetBomb_WhenUserMakesCellBomb_ThenChangeDigitToMinusOneAndSetIsBombTrue() {
        Cell cell = new Cell();
        cell.setBomb();
        Assertions.assertTrue(cell.isBomb());
        Assertions.assertEquals(-1, cell.getDigit());
    }
}
