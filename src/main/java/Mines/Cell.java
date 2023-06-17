package Mines;

public class Cell {
    private int digit;
    private boolean isBomb;

    public void setBomb() {
        isBomb = true;
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