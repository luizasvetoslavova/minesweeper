package model.mines;

public abstract class Matrix {
    final int bombCount;
    private Cell[][] cells;
    private final int lines;
    private final int cols;

    public Matrix(int lines, int cols, int bombCount) {
        this.lines = lines;
        this.cols = cols;
        this.bombCount = bombCount;
        setCells();
    }

    public Cell[][] getCells() {
        return cells;
    }

    private void setCells() {
        cells = new Cell[lines][cols];
        for (int line = 0; line < cells.length; line++) {
            for (int col = 0; col < cells[line].length; col++) {
                cells[line][col] = new Cell();
            }
        }
    }
}