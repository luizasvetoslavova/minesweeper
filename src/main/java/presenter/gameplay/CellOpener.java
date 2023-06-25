package presenter.gameplay;

import model.mines.Cell;
import model.mines.CellStatus;
import model.mines.Matrix;

import java.util.*;

public class CellOpener {
    private Matrix matrix;

    public void openNeighbors(Cell cell) {
        if (cell.getDigit() == 0) {
            openAllNeighbors(cell);
            openEmptyNeighbors(cell);
        } else {
            openRandomNeighbors(cell);
        }
    }

    public void openAllBombs() {
        Arrays.stream(matrix.getCells()).forEach(array -> Arrays.stream(array).forEach(cell -> {
            if (cell.isBomb()) cell.setCellStatus(CellStatus.OPENED);
        }));
    }

    public boolean isNeighbor(Cell cell, int line, int col) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int neighborLine = line + i;
                int neighborCol = col + j;

                if (isValidPosition(neighborLine, neighborCol) && cell == matrix.getCells()[neighborLine][neighborCol]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidPosition(int line, int col) {
        return line >= 0 && line < matrix.getCells().length && col >= 0 && col < matrix.getCells()[line].length;
    }

    private void openRandomNeighbors(Cell cell) {
        Random random = new Random();

        for (int line = 0; line < matrix.getCells().length; line++) {
            for (int col = 0; col < matrix.getCells()[line].length; col++) {
                Cell currentCell = matrix.getCells()[line][col];

                if (random.nextBoolean() && isNeighbor(cell, line, col)
                        && !currentCell.getCellStatus().equals(CellStatus.FLAGGED)
                        && !currentCell.isBomb()) {
                    currentCell.setCellStatus(CellStatus.OPENED);

                    if (currentCell.getDigit() == 0) {
                        openAllNeighbors(currentCell);
                        openEmptyNeighbors(currentCell);
                    }
                }
            }
        }
    }

    private void openAllNeighbors(Cell cell) {
        for (int line = 0; line < matrix.getCells().length; line++) {
            for (int col = 0; col < matrix.getCells()[line].length; col++) {
                Cell currentCell = matrix.getCells()[line][col];

                if (isNeighbor(cell, line, col) && !currentCell.getCellStatus().equals(CellStatus.FLAGGED)
                        && !currentCell.isBomb()) {
                    currentCell.setCellStatus(CellStatus.OPENED);

                    if (currentCell.getDigit() == 0) {
                        openEmptyNeighbors(currentCell);
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