package model.levels;

import model.mines.Matrix;

public class Custom extends Matrix {
    public Custom(int lines, int cols) {
        //20% of all cells are bombs
        super(lines, cols, (int) (0.2 * lines * cols));
    }
}