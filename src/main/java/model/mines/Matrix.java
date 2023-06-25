package model.mines;

public abstract class Matrix {
    private Cell[][] cells;
    private final int bombCount;
    private final int lines;
    private final int cols;

    public Matrix(int lines, int cols, int bombCount) {
        this.lines = lines;
        this.cols = cols;
        this.bombCount = bombCount;
        setCells();
    }

    private void setCells() {
        cells = new Cell[lines][cols];
        for (int line = 0; line < cells.length; line++) {
            for (int col = 0; col < cells[line].length; col++) {
                cells[line][col] = new Cell();
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getBombCount() {
        return bombCount;
    }
}