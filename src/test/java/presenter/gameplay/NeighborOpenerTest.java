package presenter.gameplay;

import model.levels.Easy;
import model.mines.Cell;
import model.mines.CellStatus;
import model.mines.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NeighborOpenerTest {
    private NeighborOpener opener;
    private Matrix matrix;

    @BeforeAll
    private void setUp() {
        opener = new NeighborOpener();
        matrix = new Easy();
        opener.setMatrix(matrix);
    }

    @Test
    void testOpenNeighbors_WhenOpenedCellIsEmpty_ThenOpenAllNeighbors() {
        Cell zero = getFirstEmptyCell();
        opener.openNeighbors(zero);
        getAllNeighbors(zero).forEach(neighbor -> Assertions.assertEquals(neighbor.getCellStatus(), CellStatus.OPENED));
    }

    @Test
    void testOpenNeighbors_WhenOpenedCellIsEmpty_ThenOpenAllConnectedEmptyNeighbors() {

    }

    @Test
    void testIsNeighbor_WhenComparingNeighbors_ThenReturnTrue() {

    }

    @Test
    void testIsNeighbor_WhenComparingNonNeighbors_ThenReturnFalse() {

    }

    private ArrayList<Cell> getAllNeighbors(Cell cell) {
        ArrayList<Cell> cells = new ArrayList<>();

        for (int line = 0; line < matrix.getCells().length; line++) {
            for (int col = 0; col < matrix.getCells()[line].length; col++) {
                Cell current = matrix.getCells()[line][col];

                if (opener.isNeighbor(cell, line, col) && !cell.isBomb()
                        && !current.getCellStatus().equals(CellStatus.FLAGGED)) {
                    cells.add(current);
                }
            }
        }
        return cells;
    }

    private Cell getFirstEmptyCell() {
        Cell opened = null;
        outerloop:
        for (int line = 0; line < matrix.getCells().length; line++) {
            for (int col = 0; col < matrix.getCells()[line].length; col++) {
                Cell cell = matrix.getCells()[line][col];
                if (cell.getDigit() == 0) {
                    opened = cell;
                    break outerloop;
                }
            }
        }
        return opened;
    }
}