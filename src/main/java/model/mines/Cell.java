package model.mines;

public class Cell {
    private int digit;
    private boolean isBomb;
    private CellStatus cellStatus;

    public Cell() {
        this.cellStatus = CellStatus.UNOPENED;
    }

    void setBomb() {
        isBomb = true;
        digit = -1;
    }

    void setDigit(int digit) {
        this.digit = digit;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public int getDigit() {
        return digit;
    }

    public CellStatus getCellStatus() {
        return cellStatus;
    }

    public void setCellStatus(CellStatus cellStatus) {
        this.cellStatus = cellStatus;
    }
}