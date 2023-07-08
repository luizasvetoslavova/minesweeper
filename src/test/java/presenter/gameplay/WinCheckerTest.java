package presenter.gameplay;

import model.levels.Hard;
import model.mines.Cell;
import model.mines.CellStatus;
import model.mines.Initializer;
import model.mines.Matrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class WinCheckerTest {
    private WinChecker winChecker;
    private Matrix matrix;

    @BeforeEach
    void setupWinChecker() {
        matrix = new Hard();
        Initializer.getInstance().setMatrix(matrix);
        Initializer.getInstance().initOnFirstClick(getRandomCell(), 1);
        winChecker = new WinChecker(matrix);
    }

    @Test
    void testPlayerWon_WhenAllBombsFlaggedAndAllDigitsOpened_thenReturnTrue() {
        Arrays.stream(matrix.getCells())
                .flatMap(Arrays::stream)
                .forEach(cell -> {
                    if (cell.isBomb()) cell.setCellStatus(CellStatus.FLAGGED);
                    else if (cell.getDigit() > 0) cell.setCellStatus(CellStatus.OPENED);
                });
        assertTrue(winChecker.playerWon());
    }

    @Test
    void testPlayerWon_WhenAllBombsFlaggedAndZeroDigitsOpened_thenReturnFalse() {
        Arrays.stream(matrix.getCells())
                .flatMap(Arrays::stream)
                .filter(Cell::isBomb)
                .forEach(cell -> cell.setCellStatus(CellStatus.FLAGGED));

        assertFalse(winChecker.playerWon());
    }

    @Test
    void testPlayerWon_WhenAllDigitsOpenedButZeroBombsFlagged_thenReturnFalse() {
        Arrays.stream(matrix.getCells())
                .flatMap(Arrays::stream)
                .filter(cell -> cell.getDigit() > 0)
                .forEach(cell -> cell.setCellStatus(CellStatus.OPENED));
        assertFalse(winChecker.playerWon());
    }

    private Cell getRandomCell() {
        return Arrays.stream(matrix.getCells())
                .flatMap(Arrays::stream)
                .findAny()
                .orElse(null);
    }
}