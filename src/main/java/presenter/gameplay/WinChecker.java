package presenter.gameplay;

import model.mines.Cell;
import model.mines.CellStatus;
import model.mines.Matrix;

import java.util.Arrays;
import java.util.function.Predicate;

public class WinChecker {
    private final Matrix matrix;

    public WinChecker(Matrix matrix) {
        this.matrix = matrix;
    }

    public boolean playerWon() {
        int allDigits = countCells(cell -> cell.getDigit() > 0);
        int openedDigits = countCells(cell -> cell.getDigit() > 0 && cell.getCellStatus() == CellStatus.OPENED);
        int flaggedBombs = countCells(cell -> cell.isBomb() && cell.getCellStatus() == CellStatus.FLAGGED);
        int totalBombs = countCells(Cell::isBomb);

        return (totalBombs == flaggedBombs) && (allDigits == openedDigits);
    }

    private int countCells(Predicate<Cell> condition) {
        return (int) Arrays.stream(matrix.getCells())
                .flatMap(Arrays::stream)
                .filter(condition)
                .count();
    }
}