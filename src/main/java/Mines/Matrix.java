package Mines;

public class Matrix {
    private final int rows;
    private final int cols;
    private Cell[][] cells;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
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