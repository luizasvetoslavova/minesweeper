import Levels.Hard;
import Mines.Initializer;
import Mines.Matrix;

public class Main {
    public static void main(String[] args) {
        printGame();
    }

    static void printGame() {
        Initializer i = Initializer.getInstance();
        Matrix m = new Hard();
        i.setupMatrix(m);
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
