package presenter.gameplay;

import model.levels.Easy;
import model.mines.Cell;
import model.mines.CellStatus;
import model.mines.Matrix;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

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
        Cell empty = getFirstEmptyCell();
        opener.openNeighbors(empty);
        getAllNeighbors(empty).forEach(neighbor -> assertEquals(neighbor.getCellStatus(), CellStatus.OPENED));
    }

    @Test
    void testOpenNeighbors_WhenOpenedCellIsEmpty_ThenOpenAllConnectedEmptyNeighbors() {
        opener.openNeighbors(getFirstEmptyCell());

        Arrays.stream(matrix.getCells()).forEach(array -> Arrays.stream(array).forEach(cell -> {
            if (cell.getDigit() == 0 && cell.getCellStatus().equals(CellStatus.OPENED)) {
                getAllNeighbors(cell).forEach(neighbor -> {
                    if (neighbor.getDigit() == 0) assertEquals(neighbor.getCellStatus(), CellStatus.OPENED);
                });
            }
        }));
    }

    @Test
    void testIsNeighbor_WhenComparingNeighbors_ThenReturnTrue() {
        Cell randomCell = getRandomCell();
        Cell neighbor = null;

        outerloop: for (int line1 = 0; line1 < matrix.getCells().length; line1++) {
            for (int col1 = 0; col1 < matrix.getCells()[line1].length; col1++) {
                Cell potentialNeighbor = matrix.getCells()[line1][col1];
                if (opener.isNeighbor(randomCell, line1, col1)) {
                    neighbor = potentialNeighbor;
                    break outerloop;
                }
            }
        }
        assertTrue(getAllNeighbors(randomCell).contains(neighbor));
    }

    @Test
    void testIsNeighbor_WhenComparingNonNeighbors_ThenReturnFalse() {
        Cell randomCell = getRandomCell();
        Cell nonNeighbor = null;

        outerloop:
        for (int line1 = 0; line1 < matrix.getCells().length; line1++) {
            for (int col1 = 0; col1 < matrix.getCells()[line1].length; col1++) {
                Cell potentialNonNeighbor = matrix.getCells()[line1][col1];
                if (!opener.isNeighbor(randomCell, line1, col1)) {
                    nonNeighbor = potentialNonNeighbor;
                    break outerloop;
                }
            }
        }
        assertFalse(getAllNeighbors(randomCell).contains(nonNeighbor));
    }

    private Cell getRandomCell() {
        Random random = new Random();
        int line = random.nextInt(matrix.getCells().length);
        int col = random.nextInt(matrix.getCells()[line].length);
        return matrix.getCells()[line][col];
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