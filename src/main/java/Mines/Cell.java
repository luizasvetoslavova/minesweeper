package Mines;

public class Cell {
    private int digit;
    private boolean isBomb;
    private CellStatus cellStatus;

    public void setBomb() {
        isBomb = true;
        digit = -1;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setDigit(int digit) {
        this.digit = digit;
    }

    public int getDigit() {
        return digit;
    }
}
