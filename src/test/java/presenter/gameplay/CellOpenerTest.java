package presenter.gameplay;

import model.levels.Easy;
import model.mines.Cell;
import model.mines.CellStatus;
import model.mines.Matrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CellOpenerTest {
    private CellOpener opener;
    private Matrix matrix;

    @BeforeEach
    private void initOpenerAndMatrix() {
        opener = new CellOpener();
        matrix = new Easy();
        opener.setMatrix(matrix);
    }

    @Test
    void testOpenNeighbors_WhenOpenedCellIsEmpty_ThenOpenAllNeighbors() {
        Cell empty = getFirstEmptyCell(matrix);
        opener.openNeighbors(empty);
        getAllNeighbors(empty, matrix, opener)
                .forEach(neighbor -> assertEquals(neighbor.getCellStatus(), CellStatus.OPENED));
    }

    @Test
    void testOpenNeighbors_WhenOpenedCellIsEmpty_ThenOpenAllConnectedEmptyNeighbors() {
        opener.openNeighbors(getFirstEmptyCell(matrix));

        Arrays.stream(matrix.getCells())
                .flatMap(Arrays::stream)
                .filter(cell -> cell.getDigit() == 0 && cell.getCellStatus() == CellStatus.OPENED)
                .forEach(cell -> getAllNeighbors(cell, matrix, opener)
                        .stream()
                        .filter(neighbor -> neighbor.getDigit() == 0)
                        .forEach(neighbor -> assertEquals(neighbor.getCellStatus(), CellStatus.OPENED)));
    }

    @Test
    void testOpenAllBombs_WhenBombClicked_ThenAllBombsOpened() {
        opener.openAllBombs();
        Arrays.stream(matrix.getCells())
                .flatMap(Arrays::stream)
                .filter(Cell::isBomb)
                .forEach(cell -> assertEquals(cell.getCellStatus(), CellStatus.OPENED));
    }

    private ArrayList<Cell> getAllNeighbors(Cell cell, Matrix matrix, CellOpener opener) {
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

    private Cell getFirstEmptyCell(Matrix matrix) {
        return Arrays.stream(matrix.getCells())
                .flatMap(Arrays::stream)
                .filter(cell -> cell.getDigit() == 0)
                .findFirst()
                .orElse(null);
    }
}