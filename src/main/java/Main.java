
import Mines.Initializer;
import Mines.Matrix;

public class Main {
    public static void main(String[] args) {
        Matrix m = new Matrix(9, 9);
        Initializer i = new Initializer(m, 20);
        i.setBombs();
        i.setDigits();

        for (int k = 0; k < m.getCells().length; k++) {
            for (int j = 0; j < m.getCells()[k].length; j++) {
                if (m.getCells()[k][j].isBomb()) {
                    System.out.print("⬛  ");
                } else if (m.getCells()[k][j].getDigit() == 0) {
                    System.out.print("⬜  ");
                } else {
                    System.out.print(m.getCells()[k][j].getDigit() + "  ");
                }
            }
            System.out.println();
        }
    }
}
