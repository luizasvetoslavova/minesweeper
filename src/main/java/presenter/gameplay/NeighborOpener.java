package presenter.gameplay;

import model.mines.Cell;
import model.mines.CellStatus;
import model.mines.Matrix;

import java.util.*;

public class NeighborOpener {
    private Matrix matrix;

    public void openNeighbors(Cell cell) {
        if (cell.getDigit() == 0) {
            openEmptyNeighbors(cell);
        } else {
            openRandomEmptyNeighbors(cell);
        }
    }

    public boolean isNeighbor(Cell cell, int line, int col) {
        return (line != 0 && col != 0 && cell == matrix.getCells()[line - 1][col - 1])
                || (line != 0 && cell == matrix.getCells()[line - 1][col])
                || (line != 0 && col != matrix.getCells()[line].length - 1 && cell == matrix.getCells()[line - 1][col + 1])
                || (col != 0 && cell == matrix.getCells()[line][col - 1])
                || (col != matrix.getCells()[line].length - 1 && cell == matrix.getCells()[line][col + 1])
                || (line != matrix.getCells().length - 1 && col != 0 && cell == matrix.getCells()[line + 1][col - 1])
                || (line != matrix.getCells().length - 1 && cell == matrix.getCells()[line + 1][col])
                || (line != matrix.getCells().length - 1 && col != matrix.getCells()[line].length - 1
                && cell == matrix.getCells()[line + 1][col + 1]);
    }

    private void openRandomEmptyNeighbors(Cell cell) {
        Random random = new Random();

        for (int line = 0; line < matrix.getCells().length; line++) {
            for (int col = 0; col < matrix.getCells()[line].length; col++) {
                Cell currentCell = matrix.getCells()[line][col];

                if (random.nextBoolean() && isNeighbor(cell, line, col)
                        && !currentCell.getCellStatus().equals(CellStatus.FLAGGED)
                        && !currentCell.isBomb()) {
                    currentCell.setCellStatus(CellStatus.OPENED);

                    if (currentCell.getDigit() == 0) {
                        openEmptyNeighbors(matrix.getCells()[line][col]);
                    }
                }
            }
        }
    }

    private void openEmptyNeighbors(Cell cell) {
        Set<Cell> openedCells = new HashSet<>();
        Queue<Cell> queue = new LinkedList<>();
        queue.offer(cell);

        while (!queue.isEmpty()) {
            Cell currentCell = queue.poll();
            openedCells.add(currentCell);
            currentCell.setCellStatus(CellStatus.OPENED);

            if (currentCell.getDigit() == 0) {
                for (int line = 0; line < matrix.getCells().length; line++) {
                    for (int col = 0; col < matrix.getCells()[line].length; col++) {
                        Cell neighbor = matrix.getCells()[line][col];
                        if (isNeighbor(currentCell, line, col)
                                && !neighbor.getCellStatus().equals(CellStatus.FLAGGED)
                                && !neighbor.isBomb()
                                && !openedCells.contains(neighbor)) {
                            queue.offer(neighbor);
                        }
                    }
                }
            }
        }
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }
}