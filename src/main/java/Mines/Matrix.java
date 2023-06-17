package Mines;

public abstract class Matrix {
    final int bombCount;
    private Cell[][] cells;
    private final int rows;
    private final int cols;

    public Matrix(int rows, int cols, int bombCount) {
        this.rows = rows;
        this.cols = cols;
        this.bombCount = bombCount;
        setCells();
    }

    public Cell[][] getCells() {
        return cells;
    }

    private void setCells() {
        cells = new Cell[rows][cols];
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                cells[row][col] = new Cell();
            }
        }
    }
}